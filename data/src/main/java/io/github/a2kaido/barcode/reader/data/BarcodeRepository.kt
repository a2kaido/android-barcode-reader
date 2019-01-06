package io.github.a2kaido.barcode.reader.data

import io.github.a2kaido.barcode.reader.data.room.BarcodeDatabase
import io.github.a2kaido.barcode.reader.data.room.BarcodeEntity
import io.github.a2kaido.barcode.reader.data.room.BarcodeType
import io.github.a2kaido.barcode.reader.domain.data.BarcodeRepositoryInterface
import io.github.a2kaido.barcode.reader.domain.model.BarcodeData
import io.github.a2kaido.barcode.reader.domain.model.RawDataBarcode
import io.github.a2kaido.barcode.reader.domain.model.UrlBarcode

class BarcodeRepository(private val database: BarcodeDatabase) : BarcodeRepositoryInterface {

    override fun saveBarcode(barcode: BarcodeData) {
        val barcodeEntity = BarcodeEntity(
            code = barcode.code, format = barcode.format, date = barcode.date, type = when (barcode) {
                is UrlBarcode -> {
                    BarcodeType.URL
                }
                is RawDataBarcode -> {
                    BarcodeType.RAW_DATA
                }
            }
        )
        database.barcodeDao.insertBarcode(barcodeEntity)
    }

    override fun getBarcodes(): List<BarcodeData> = database.barcodeDao.selectBarcodes().asSequence().map {
        when (it.type) {
            BarcodeType.URL -> {
                UrlBarcode(it.code, it.format, it.date)
            }
            BarcodeType.RAW_DATA -> {
                RawDataBarcode(it.code, it.format, it.date)
            }
        }
    }.toList()
}
