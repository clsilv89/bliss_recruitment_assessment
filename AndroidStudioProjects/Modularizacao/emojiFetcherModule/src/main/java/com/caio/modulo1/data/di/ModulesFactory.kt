package com.caio.modulo1.data.di

import com.caio.modulo1.BuildConfig
import com.caio.modulo1.data.RestConfig
import com.caio.modulo1.data.api.Api
import com.caio.modulo1.data.repository.Repository
import com.caio.modulo1.ui.ViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ModulesFactory {

    const val MODULE = "modulo"
    val module = module {
        scope(named(MODULE)) {
            scoped<Api> { RestConfig.service(BuildConfig.BASE_URL) }
            scoped { Repository(get()) }
            scoped { ViewModel(get()) }
        }
    }
}

