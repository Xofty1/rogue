package api.di

import api.service.TestService
import org.koin.dsl.module

val appModule = module {
    single { TestService() }
    //singleOf(::TestService)
}