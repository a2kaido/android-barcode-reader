package io.github.a2kaido.barcode.reader.mapper

import io.github.a2kaido.barcode.reader.domain.model.BarcodeFormat

fun com.google.zxing.BarcodeFormat.toDomain() = when (this) {
    com.google.zxing.BarcodeFormat.AZTEC -> BarcodeFormat.AZTEC
    com.google.zxing.BarcodeFormat.CODABAR -> BarcodeFormat.CODABAR
    com.google.zxing.BarcodeFormat.CODE_39 -> BarcodeFormat.CODE_39
    com.google.zxing.BarcodeFormat.CODE_93 -> BarcodeFormat.CODE_93
    com.google.zxing.BarcodeFormat.CODE_128 -> BarcodeFormat.CODE_128
    com.google.zxing.BarcodeFormat.DATA_MATRIX -> BarcodeFormat.DATA_MATRIX
    com.google.zxing.BarcodeFormat.EAN_8 -> BarcodeFormat.EAN_8
    com.google.zxing.BarcodeFormat.EAN_13 -> BarcodeFormat.EAN_13
    com.google.zxing.BarcodeFormat.ITF -> BarcodeFormat.ITF
    com.google.zxing.BarcodeFormat.MAXICODE -> BarcodeFormat.MAXICODE
    com.google.zxing.BarcodeFormat.PDF_417 -> BarcodeFormat.PDF_417
    com.google.zxing.BarcodeFormat.QR_CODE -> BarcodeFormat.QR_CODE
    com.google.zxing.BarcodeFormat.RSS_14 -> BarcodeFormat.RSS_14
    com.google.zxing.BarcodeFormat.RSS_EXPANDED -> BarcodeFormat.RSS_EXPANDED
    com.google.zxing.BarcodeFormat.UPC_A -> BarcodeFormat.UPC_A
    com.google.zxing.BarcodeFormat.UPC_E -> BarcodeFormat.UPC_E
    com.google.zxing.BarcodeFormat.UPC_EAN_EXTENSION -> BarcodeFormat.UPC_EAN_EXTENSION
}