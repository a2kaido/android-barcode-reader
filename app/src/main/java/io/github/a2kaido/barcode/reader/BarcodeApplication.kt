package io.github.a2kaido.barcode.reader

import android.app.Application
import io.github.a2kaido.barcode.reader.di.myModule
import org.koin.android.ext.android.startKoin

class BarcodeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(myModule))
    }
}