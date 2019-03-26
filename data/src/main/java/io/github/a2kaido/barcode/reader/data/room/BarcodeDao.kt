package io.github.a2kaido.barcode.reader.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BarcodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBarcode(barcode: BarcodeEntity)

    @Delete
    fun deleteBarcode(barcode: BarcodeEntity)

    @Query("SELECT * FROM barcodes ORDER BY date DESC")
    fun selectBarcodes(): LiveData<List<BarcodeEntity>>
}
