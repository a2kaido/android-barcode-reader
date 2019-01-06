package io.github.a2kaido.barcode.reader.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.a2kaido.barcode.reader.domain.model.BarcodeFormat
import java.util.*

@Entity(tableName = "barcodes")
@TypeConverters(BarcodeFormatConverter::class, BarcodeTypeConverter::class, DateConverter::class)
data class BarcodeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val code: String,
    val format: BarcodeFormat,
    val date: Date,
    val type: BarcodeType
)

enum class BarcodeType {
    URL, RAW_DATA
}