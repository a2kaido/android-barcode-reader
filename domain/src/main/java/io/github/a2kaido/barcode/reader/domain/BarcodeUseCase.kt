package io.github.a2kaido.barcode.reader.domain

import io.github.a2kaido.barcode.reader.domain.data.BarcodeRepositoryInterface
import io.github.a2kaido.barcode.reader.domain.model.BarcodeData

class BarcodeUseCase(private val repository: BarcodeRepositoryInterface) : BarcodeUseCaseInterface {

    override fun saveBarcode(barcode: BarcodeData) {
        repository.saveBarcode(barcode)
    }
}

interface BarcodeUseCaseInterface {

    fun saveBarcode(barcode: BarcodeData)
}
