package com.caio.bliss.data.di

import org.koin.core.Koin
import org.koin.core.KoinComponent

interface CustomKoinComponent : KoinComponent {
    override fun getKoin(): Koin = KoinContext.koin
}
