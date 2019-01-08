package io.github.a2kaido.barcode.reader.domain

import androidx.lifecycle.LiveData
import io.github.a2kaido.barcode.reader.domain.data.BarcodeRepositoryInterface
import io.github.a2kaido.barcode.reader.domain.model.BarcodeData

class BarcodeUseCase(private val repository: BarcodeRepositoryInterface) : BarcodeUseCaseInterface {

    override fun saveBarcode(barcode: BarcodeData) {
        repository.saveBarcode(barcode)
    }

    override fun deleteBarcode(barcode: BarcodeData) {
        repository.deleteBarcode(barcode)
    }

    override fun getBarcodes(): LiveData<List<BarcodeData>> = repository.getBarcodes()
}

interface BarcodeUseCaseInterface {

    fun saveBarcode(barcode: BarcodeData)

    fun deleteBarcode(barcode: BarcodeData)

    fun getBarcodes(): LiveData<List<BarcodeData>>
}
