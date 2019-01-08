package io.github.a2kaido.barcode.reader.domain.data

import androidx.lifecycle.LiveData
import io.github.a2kaido.barcode.reader.domain.model.BarcodeData

interface BarcodeRepositoryInterface {

    fun saveBarcode(barcode: BarcodeData)

    fun deleteBarcode(barcode: BarcodeData)

    fun getBarcodes(): LiveData<List<BarcodeData>>
}