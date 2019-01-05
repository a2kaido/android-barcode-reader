package io.github.a2kaido.barcode.reader.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BarcodeEntity::class], version = 1, exportSchema = false)
abstract class BarcodeDatabase : RoomDatabase() {
    abstract val barcodeDao: BarcodeDao
}
