package com.caio.modulo1.data.di

import android.content.Context
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.android.ext.koin.androidContext

object KoinContext {
    private lateinit var appContext: Context

    private val koinApplication: KoinApplication by lazy {
        koinApplication {
            androidContext(appContext)
            modules(ModulesFactory.module)
        }
    }
    val koin by lazy {
        koinApplication.koin
    }

    fun start(context: Context) {
        appContext = context
    }
}