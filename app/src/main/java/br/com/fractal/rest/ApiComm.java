package br.com.fractal.rest;

import android.util.Log;
import br.com.fractal.BuildConfig;
import br.com.fractal.rest.endpoint.SearchEndPoint;
import com.google.gson.*;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.joda.time.DateTime;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiComm {

    private static final String TAG = ApiComm.class.getSimpleName();
    private static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static Retrofit mRetrofit;
    private static OkHttpClient mHttpClient;
    private static Map<String, Object> mServicesPool = new HashMap<>();
    private static String URL_BASE = BuildConfig.DEBUG ? "https://sandbox-api.brewerydb.com/" : "https://api.brewerydb.com/";
    private static ApiComm instance = null;
    public final static String KEY = "92a3380cde19ad1f95b71c38dd571435";
    private static Gson gson;

    @Inject
    public ApiComm() {
        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                String callUrl = chain.request().url().toString();
                builder.header("Content-Type", "application/json");
                builder.header("Connection", "close");
                builder.header("Keep-Alive", "false");
                builder.header("key", "92a3380cde19ad1f95b71c38dd571435");
                return chain.proceed(builder.build());
            }
        };

        // Logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        // Add the interceptor to OkHttpClient
        mHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build();


        // GSON Parser
        gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .registerTypeAdapter(Date.class, new DateTypeConverter())
                .create();


        // Build the retrofit object
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mHttpClient)
                .build();
    }

    public static synchronized ApiComm getInstance() {
        if (instance == null) {
            instance = new ApiComm();
        }
        return instance;
    }

    /**
     * Creates a service
     */
    public <T> T create(final Class<T> service) {
        String key = service.getSimpleName();
        if (!mServicesPool.containsKey(key)) {
            mServicesPool.put(key, mRetrofit.create(service));
        }
        return (T) mServicesPool.get(key);
    }

    public String getBaseUrl() {
        return mRetrofit.baseUrl().url().toString();
    }

    /**
     * Set base URL via reflection.
     */
    public void setBaseUrl(String newUrl) throws NoSuchFieldException, IllegalAccessException {
        Field field = Retrofit.class.getDeclaredField("baseUrl");
        field.setAccessible(true);
        HttpUrl newHttpUrl = HttpUrl.parse(newUrl);
        field.set(mRetrofit, newHttpUrl);
    }

    public SearchEndPoint searchEndPoint() {
        return create(SearchEndPoint.class);
    }

    public static class DateTimeTypeConverter
            implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
        @Override
        public JsonElement serialize(DateTime src, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                return new DateTime(json.getAsString());
            } catch (IllegalArgumentException e) {
                // May be it came in formatted as row_chat_others java.util.Date, so try that
                Date date = context.deserialize(json, Date.class);
                return new DateTime(date);
            }
        }
    }

    private class DateTypeConverter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        @Override
        public JsonElement serialize(Date src, Type srcType, JsonSerializationContext context) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            return new JsonPrimitive(sdf.format(src));
        }

        @Override
        public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            Date date = null;
            try {
                date = new Date(json.getAsLong());
            } catch (Exception e) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    date = sdf.parse(json.getAsString());
                } catch (Exception e1) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        date = sdf.parse(json.getAsString());
                    } catch (Exception e2) {
                        Log.e(TAG, "Erro ao converter data. Não foi possível encontrar uma forma de conversão " + json.getAsString(), e);
                    }
                }
            }
            return date;
        }
    }
}
