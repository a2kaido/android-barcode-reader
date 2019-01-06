package io.github.a2kaido.barcode.reader.domain.model

import android.net.Uri
import java.io.Serializable
import java.util.Date

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

class BarcodeFactory {
    companion object {
        fun create(text: String, format: BarcodeFormat): BarcodeData = try {
            val uri = Uri.parse(text)
            if (uri.scheme == "http" || uri.scheme == "https") {
                UrlBarcode(code = uri.toString(), format = format, date = Date())
            } else {
                RawDataBarcode(code = text, format = format, date = Date())
            }
        } catch (e: Exception) {
            RawDataBarcode(code = text, format = format, date = Date())
        }
    }
}

interface Barcode {
    val id: Long?
    val code: String
    val format: BarcodeFormat
    val date: Date
}
