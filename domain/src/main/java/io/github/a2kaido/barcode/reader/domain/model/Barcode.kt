package io.github.a2kaido.barcode.reader.domain.model

import android.net.Uri
import io.github.a2kaido.emvco.parseEMVCo
import java.io.Serializable
import java.util.*

sealed class BarcodeData : Barcode, Serializable

data class UrlBarcode(
    override val id: Long? = null,
    override val code: String,
    override val format: BarcodeFormat,
    override val date: Date
) : BarcodeData()

data class RawDataBarcode(
    override val id: Long? = null,
    override val code: String,
    override val format: BarcodeFormat,
    override val date: Date
) : BarcodeData()

data class WifiBarcode(
    override val id: Long? = null,
    override val code: String,
    override val format: BarcodeFormat,
    override val date: Date
) : BarcodeData()

data class EMVCoBarcode(
    override val id: Long? = null,
    override val code: String,
    override val format: BarcodeFormat,
    override val date: Date
) : BarcodeData()

class BarcodeFactory {
    companion object {
        fun create(text: String, format: BarcodeFormat): BarcodeData {
            if (isUrl(text)) {
                return UrlBarcode(code = text, format = format, date = Date())
            }

            if (isWifi(text)) {
                return WifiBarcode(code = text, format = format, date = Date())
            }

            if (isEMVCo(text)) {
                return EMVCoBarcode(code = text, format = format, date = Date())
            }

            return RawDataBarcode(code = text, format = format, date = Date())
        }
    }
}

private fun isUrl(text: String) = when (Uri.parse(text).scheme) {
    "http", "https" -> true
    else -> false
}

private fun isWifi(text: String): Boolean {
    val regex = Regex("^WIFI:T:(.+?);S:(.+?);P:(.+?);;$")
    return regex.matches(text)
}

private fun isEMVCo(text: String): Boolean = try {
    parseEMVCo(text)
    true
} catch (e: Exception) {
    false
}

interface Barcode {
    val id: Long?
    val code: String
    val format: BarcodeFormat
    val date: Date
}
