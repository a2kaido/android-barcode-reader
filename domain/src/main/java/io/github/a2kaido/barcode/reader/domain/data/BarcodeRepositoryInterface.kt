package io.github.a2kaido.barcode.reader.domain.data

import io.github.a2kaido.barcode.reader.domain.model.BarcodeData

interface BarcodeRepositoryInterface {

    fun saveBarcode(barcode: BarcodeData)

    fun getBarcodes(): List<BarcodeData>
}