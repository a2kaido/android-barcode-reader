package io.github.a2kaido.barcode.reader.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BarcodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBarcode(barcode: BarcodeEntity)
}
