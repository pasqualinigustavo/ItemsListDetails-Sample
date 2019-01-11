package br.com.fractal.rest.endpoint

import br.com.fractal.rest.ApiComm
import br.com.fractal.rest.response.GetBeerResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchEndPoint {
    @GET("/v2/beers?key=" + ApiComm.KEY)
    fun beersList(
        @Query("p") per_page: Int
    ): Observable<GetBeerResponse>
}