package com.caio.bliss.application

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.caio.bliss.data.di.CustomKoinComponent
import com.caio.bliss.data.di.KoinContext
import com.caio.bliss.data.di.ModulesFactory
import com.caio.bliss.data.room.EmojiDatabase
import com.caio.bliss.data.room.UserDatabase
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MyApplication : Application(), CustomKoinComponent {

    companion object {
        var emojiDatabase: EmojiDatabase? = null
        var userDatabase: UserDatabase? = null
        var scope: Scope? = null
        private const val SCOPE = "scope"
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this)
        emojiDatabase = Room.databaseBuilder(
            this,
            EmojiDatabase::class.java,
            "emoji-list-database"
        )
            .allowMainThreadQueries()
            .build()

        userDatabase = Room.databaseBuilder(
            this,
            UserDatabase::class.java,
            "user-list-database"
        )
            .allowMainThreadQueries()
            .build()

        scope = getKoin().getOrCreateScope(SCOPE, named(ModulesFactory.MODULE))
    }

    private fun startKoin(context: Context) {
        KoinContext.start(this)
    }
}
