package io.a2kaido.barcode.reader.settings

import android.content.Context
import android.content.Intent

object OssLicensesMenuIntentBuilder {
    var buildIntent: ((context: Context, title: String) -> Intent)? = null
}
