package br.com.fractal.di.modules

import br.com.fractal.database.DaoFactory
import br.com.fractal.rest.ApiComm
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun apiComm() = ApiComm.getInstance()

    @Provides
    @Singleton
    fun daoFactory() = DaoFactory.getInstance()
}