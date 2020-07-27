package io.github.a2kaido.barcode.reader.data.room

import androidx.room.TypeConverter
import io.github.a2kaido.barcode.reader.domain.model.BarcodeFormat
import java.util.Date

class BarcodeTypeConverter {

    @TypeConverter
    fun toEntity(type: Int) = when (type) {
        BarcodeType.URL.ordinal -> BarcodeType.URL
        BarcodeType.RAW_DATA.ordinal -> BarcodeType.RAW_DATA
        BarcodeType.WIFI.ordinal -> BarcodeType.WIFI
        BarcodeType.EMVCo.ordinal -> BarcodeType.EMVCo
        BarcodeType.EMVCoCPM.ordinal -> BarcodeType.EMVCoCPM
        else -> BarcodeType.RAW_DATA
    }

    @TypeConverter
    fun toDb(type: BarcodeType) = when (type) {
        BarcodeType.URL -> BarcodeType.URL.ordinal
        BarcodeType.RAW_DATA -> BarcodeType.RAW_DATA.ordinal
        BarcodeType.WIFI -> BarcodeType.WIFI.ordinal
        BarcodeType.EMVCo -> BarcodeType.EMVCo.ordinal
        BarcodeType.EMVCoCPM -> BarcodeType.EMVCoCPM.ordinal
    }
}

class BarcodeFormatConverter {

    @TypeConverter
    fun toEntity(format: Int) = when (format) {
        BarcodeFormat.AZTEC.ordinal -> BarcodeFormat.AZTEC
        BarcodeFormat.CODABAR.ordinal -> BarcodeFormat.CODABAR
        BarcodeFormat.CODE_39.ordinal -> BarcodeFormat.CODE_39
        BarcodeFormat.CODE_93.ordinal -> BarcodeFormat.CODE_93
        BarcodeFormat.CODE_128.ordinal -> BarcodeFormat.CODE_128
        BarcodeFormat.DATA_MATRIX.ordinal -> BarcodeFormat.DATA_MATRIX
        BarcodeFormat.EAN_8.ordinal -> BarcodeFormat.EAN_8
        BarcodeFormat.EAN_13.ordinal -> BarcodeFormat.EAN_13
        BarcodeFormat.ITF.ordinal -> BarcodeFormat.ITF
        BarcodeFormat.MAXICODE.ordinal -> BarcodeFormat.MAXICODE
        BarcodeFormat.PDF_417.ordinal -> BarcodeFormat.PDF_417
        BarcodeFormat.QR_CODE.ordinal -> BarcodeFormat.QR_CODE
        BarcodeFormat.RSS_14.ordinal -> BarcodeFormat.RSS_14
        BarcodeFormat.RSS_EXPANDED.ordinal -> BarcodeFormat.RSS_EXPANDED
        BarcodeFormat.UPC_A.ordinal -> BarcodeFormat.UPC_A
        BarcodeFormat.UPC_E.ordinal -> BarcodeFormat.UPC_E
        BarcodeFormat.UPC_EAN_EXTENSION.ordinal -> BarcodeFormat.UPC_EAN_EXTENSION
        else -> BarcodeFormat.QR_CODE
    }

    @TypeConverter
    fun toDb(type: BarcodeFormat) = when (type) {
        BarcodeFormat.AZTEC -> BarcodeFormat.AZTEC.ordinal
        BarcodeFormat.CODABAR -> BarcodeFormat.CODABAR.ordinal
        BarcodeFormat.CODE_39 -> BarcodeFormat.CODE_39.ordinal
        BarcodeFormat.CODE_93 -> BarcodeFormat.CODE_93.ordinal
        BarcodeFormat.CODE_128 -> BarcodeFormat.CODE_128.ordinal
        BarcodeFormat.DATA_MATRIX -> BarcodeFormat.DATA_MATRIX.ordinal
        BarcodeFormat.EAN_8 -> BarcodeFormat.EAN_8.ordinal
        BarcodeFormat.EAN_13 -> BarcodeFormat.EAN_13.ordinal
        BarcodeFormat.ITF -> BarcodeFormat.ITF.ordinal
        BarcodeFormat.MAXICODE -> BarcodeFormat.MAXICODE.ordinal
        BarcodeFormat.PDF_417 -> BarcodeFormat.PDF_417.ordinal
        BarcodeFormat.QR_CODE -> BarcodeFormat.QR_CODE.ordinal
        BarcodeFormat.RSS_14 -> BarcodeFormat.RSS_14.ordinal
        BarcodeFormat.RSS_EXPANDED -> BarcodeFormat.RSS_EXPANDED.ordinal
        BarcodeFormat.UPC_A -> BarcodeFormat.UPC_A.ordinal
        BarcodeFormat.UPC_E -> BarcodeFormat.UPC_E.ordinal
        BarcodeFormat.UPC_EAN_EXTENSION -> BarcodeFormat.UPC_EAN_EXTENSION.ordinal
    }
}

class DateConverter {

    @TypeConverter
    fun fromTimeToDate(time: Long?): Date? {
        return time?.let { Date(it) }
    }

    @TypeConverter
    fun fromDateToTime(date: Date?): Long? {
        return date?.time
    }
}
