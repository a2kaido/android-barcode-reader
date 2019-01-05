package io.github.a2kaido.barcode.reader.domain

import io.github.a2kaido.barcode.reader.domain.model.BarcodeData

class BarcodeUseCase : BarcodeUseCaseInterface {

    override fun saveBarcode(barcode: BarcodeData) {

    }
}

interface BarcodeUseCaseInterface {

    fun saveBarcode(barcode: BarcodeData)
}
