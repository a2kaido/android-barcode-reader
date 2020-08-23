package io.github.a2kaido.barcode.reader.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.github.a2kaido.barcode.reader.data.room.BarcodeDatabase
import io.github.a2kaido.barcode.reader.data.room.BarcodeEntity
import io.github.a2kaido.barcode.reader.data.room.BarcodeType
import io.github.a2kaido.barcode.reader.domain.data.BarcodeRepositoryInterface
import io.github.a2kaido.barcode.reader.domain.model.*

class BarcodeRepository(private val database: BarcodeDatabase) : BarcodeRepositoryInterface {

    override fun saveBarcode(barcode: BarcodeData) {
        val barcodeEntity = BarcodeEntity(
            code = barcode.code,
            format = barcode.format,
            date = barcode.date,
            type = when (barcode) {
                is UrlBarcode -> BarcodeType.URL
                is RawDataBarcode -> BarcodeType.RAW_DATA
                is WifiBarcode -> BarcodeType.WIFI
                is EMVCoBarcode -> BarcodeType.EMVCo
                is EMVCoCPMBarcode -> BarcodeType.EMVCoCPM
            }
        )
        database.barcodeDao.insertBarcode(barcodeEntity)
    }

    override fun deleteBarcode(barcode: BarcodeData) {
        database.barcodeDao.deleteBarcode(
            BarcodeEntity(
                barcode.id, barcode.code, barcode.format, barcode.date, when (barcode) {
                    is UrlBarcode -> BarcodeType.URL
                    is RawDataBarcode -> BarcodeType.RAW_DATA
                    is WifiBarcode -> BarcodeType.WIFI
                    is EMVCoBarcode -> BarcodeType.EMVCo
                    is EMVCoCPMBarcode -> BarcodeType.EMVCoCPM
                }
            )
        )
    }

    override fun getBarcodes(): LiveData<List<BarcodeData>> =
        Transformations.map(database.barcodeDao.selectBarcodes()) { barcodeList ->
            barcodeList.asSequence().map {
                when (it.type) {
                    BarcodeType.URL -> UrlBarcode(it.id, it.code, it.format, it.date)
                    BarcodeType.RAW_DATA -> RawDataBarcode(it.id, it.code, it.format, it.date)
                    BarcodeType.WIFI -> WifiBarcode(it.id, it.code, it.format, it.date)
                    BarcodeType.EMVCo -> EMVCoBarcode(it.id, it.code, it.format, it.date)
                    BarcodeType.EMVCoCPM -> EMVCoCPMBarcode(it.id, it.code, it.format, it.date)
                }
            }.toList()
        }
}
