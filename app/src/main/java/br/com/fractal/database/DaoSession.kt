package br.com.fractal.database

class DaoSession internal constructor(private val daoMaster: DaoMaster) {
    val beerDao: BeerDao = BeerDao(daoMaster.database, daoMaster.devOpenHelper)
}
