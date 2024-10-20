package io.github.a2kaido.barcode.reader

import android.app.Application
import android.content.Intent
import com.facebook.stetho.Stetho
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import io.a2kaido.barcode.reader.settings.OssLicensesMenuIntentBuilder
import io.github.a2kaido.barcode.reader.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class BarcodeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BarcodeApplication)
            modules(myModule)
        }

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        OssLicensesMenuIntentBuilder.buildIntent = { context, title ->
            OssLicensesMenuActivity.setActivityTitle(title)
            Intent(context, OssLicensesMenuActivity::class.java)
        }
    }
}
