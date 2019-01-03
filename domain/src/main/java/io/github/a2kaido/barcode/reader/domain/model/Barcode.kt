package io.github.a2kaido.barcode.reader.domain.model

import android.net.Uri
import java.io.Serializable
import java.util.*

sealed class BarcodeData : Barcode, Serializable

data class UrlBarcode(
    override val code: String,
    override val format: BarcodeFormat,
    override val date: Date
) : BarcodeData()

data class RawDataBarcode(
    override val code: String,
    override val format: BarcodeFormat,
    override val date: Date
) : BarcodeData()

class BarcodeFactory {
    companion object {
        fun create(text: String, format: BarcodeFormat): BarcodeData = try {
            val uri = Uri.parse(text)
            if (uri.scheme == "http" || uri.scheme == "https") {
                UrlBarcode(uri.toString(), format, Date())
            } else {
                RawDataBarcode(text, format, Date())
            }
        } catch (e: Exception) {
            RawDataBarcode(text, format, Date())
        }
    }
}

interface Barcode {
    val code: String
    val format: BarcodeFormat
    val date: Date
}
