package com.caio.bliss.data.di

import com.caio.bliss.BuildConfig
import com.caio.bliss.data.network.RestConfig
import com.caio.bliss.data.api.Api
import com.caio.bliss.data.repository.Repository
import com.caio.bliss.ui.viewModel.MainActivityViewModel
import com.caio.bliss.ui.viewModel.ListEmojiViewModel
import com.caio.bliss.ui.viewModel.ListReposActvityViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ModulesFactory {

    const val MODULE = "modulo"
    val module = module {
        scope(named(MODULE)) {
            scoped<Api> { RestConfig.service(BuildConfig.BASE_URL) }
            scoped { Repository(get()) }
            scoped { MainActivityViewModel(get()) }
            scoped { ListEmojiViewModel(get()) }
            scoped { ListReposActvityViewModel(get()) }
        }
    }
}

