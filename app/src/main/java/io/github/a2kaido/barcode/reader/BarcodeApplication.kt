package io.github.a2kaido.barcode.reader

import android.app.Application
import com.facebook.stetho.Stetho
import io.github.a2kaido.barcode.reader.di.myModule
import org.koin.android.ext.android.startKoin

@Suppress("unused")
class BarcodeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(myModule))

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}