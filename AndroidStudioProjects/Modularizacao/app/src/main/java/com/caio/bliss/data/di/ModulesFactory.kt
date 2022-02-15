package com.caio.bliss.data.di

import com.caio.bliss.BuildConfig
import com.caio.bliss.data.api.Api
import com.caio.bliss.data.network.NetWorkHelp
import com.caio.bliss.data.network.  RestConfig
import com.caio.bliss.data.repository.Repository
import com.caio.bliss.ui.viewModel.ListEmojiViewModel
import com.caio.bliss.ui.viewModel.ListReposActvityViewModel
import com.caio.bliss.ui.viewModel.MainActivityViewModel
import com.caio.bliss.usecases.GetEmojiUseCaseImpl
import com.caio.bliss.usecases.GetReposUseCaseImpl
import com.caio.bliss.usecases.GetUserUseCaseImpl
import com.caio.bliss.usecases.IGetEmojiUseCase
import com.caio.bliss.usecases.IGetReposUseCase
import com.caio.bliss.usecases.IGetUserUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ModulesFactory {
    const val MODULE = "modulo"
    val module = module {
        scope(named(MODULE)) {
            scoped<Api> { RestConfig.service(BuildConfig.BASE_URL) }
            scoped { NetWorkHelp }
            scoped { Repository(get(), get()) }
            scoped { MainActivityViewModel(get(), get(), get()) }
            scoped { ListEmojiViewModel(get()) }
            scoped { ListReposActvityViewModel(get()) }
            scoped<IGetEmojiUseCase> { GetEmojiUseCaseImpl(get()) }
            scoped<IGetUserUseCase> { GetUserUseCaseImpl(get()) }
            scoped<IGetReposUseCase> { GetReposUseCaseImpl(get()) }
        }
    }
}
