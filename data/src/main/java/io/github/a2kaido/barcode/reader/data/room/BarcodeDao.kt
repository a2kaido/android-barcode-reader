package io.github.a2kaido.barcode.reader.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BarcodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBarcode(barcode: BarcodeEntity)

    @Query("SELECT * FROM barcodes")
    fun selectBarcodes(): List<BarcodeEntity>
}
