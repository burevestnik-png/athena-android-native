package ru.yofik.athena

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDateTime
import timber.log.Timber

class MessengerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
