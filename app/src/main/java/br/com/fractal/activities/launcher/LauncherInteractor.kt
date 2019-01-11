package br.com.fractal.activities.launcher

import br.com.fractal.database.DaoFactory
import br.com.fractal.rest.ApiComm

class LauncherInteractor(
    private val apiComm: ApiComm,
    private val daoFactory: DaoFactory
) {


//    /**
//     * Força uma primeira abertura do banco de dados para executar update no schema se necessário.
//     */
//    fun initializeDatabase(): Observable<List<CadastroDomiciliar>> {
//        return Observable.fromCallable {
//            daoFactory.cadastroDomiciliarDao.loadAll()
//        }.observeOn(AndroidSchedulers.mainThread())
//         .subscribeOn(Schedulers.io())
//    }
}