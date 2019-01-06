package io.github.a2kaido.barcode.reader

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.github.a2kaido.barcode.reader.domain.model.BarcodeData
import io.github.a2kaido.barcode.reader.domain.model.RawDataBarcode
import io.github.a2kaido.barcode.reader.domain.model.UrlBarcode
import kotlinx.android.synthetic.main.item_barcode.*

class BarcodeItem(private val viewModel: HistoryViewModel, private val barcode: BarcodeData) : Item() {

    override fun getLayout() = R.layout.item_barcode

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.item_text.text = barcode.code
        viewHolder.item_barcode_type.text = when (barcode) {
            is UrlBarcode -> {
                viewHolder.itemView.context.getString(R.string.barcode_type_url)
            }
            is RawDataBarcode -> {
                viewHolder.itemView.context.getString(R.string.barcode_type_raw)
            }
        }
        viewHolder.item_barcode_format.text = barcode.format.name
        viewHolder.itemView.setOnClickListener {
            viewModel.onClickHistoryItem(barcode)
        }
        viewHolder.itemView.setOnLongClickListener {
            viewModel.onLongClickHistoryItem(barcode)
            true
        }
    }
}