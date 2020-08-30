package io.a2kaido.barcode.reader.history

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import io.a2kaido.barcode.reader.history.databinding.ItemBarcodeBinding
import io.github.a2kaido.barcode.reader.domain.model.BarcodeData
import io.github.a2kaido.barcode.reader.domain.model.EMVCoBarcode
import io.github.a2kaido.barcode.reader.domain.model.EMVCoCPMBarcode
import io.github.a2kaido.barcode.reader.domain.model.RawDataBarcode
import io.github.a2kaido.barcode.reader.domain.model.UrlBarcode
import io.github.a2kaido.barcode.reader.domain.model.WifiBarcode

class BarcodeItem(
    private val viewModel: HistoryViewModel,
    private val barcode: BarcodeData
) : BindableItem<ItemBarcodeBinding>() {

    override fun initializeViewBinding(view: View): ItemBarcodeBinding {
        return ItemBarcodeBinding.bind(view)
    }

    override fun getLayout(): Int = R.layout.item_barcode

    override fun bind(viewBinding: ItemBarcodeBinding, position: Int) {
        viewBinding.itemText.text = barcode.code
        viewBinding.itemBarcodeType.text = when (barcode) {
            is UrlBarcode -> {
                viewBinding.root.context.getString(R.string.barcode_type_url)
            }
            is RawDataBarcode -> {
                viewBinding.root.context.getString(R.string.barcode_type_raw)
            }
            is WifiBarcode -> {
                viewBinding.root.context.getString(R.string.barcode_type_wifi)
            }
            is EMVCoBarcode -> {
                viewBinding.root.context.getString(R.string.barcode_type_emvco_mpm)
            }
            is EMVCoCPMBarcode -> {
                viewBinding.root.context.getString(R.string.barcode_type_emvco_cpm)
            }
        }
        viewBinding.itemBarcodeFormat.text = barcode.format.name
        viewBinding.root.setOnClickListener {
            viewModel.onClickHistoryItem(barcode)
        }
        viewBinding.root.setOnLongClickListener {
            viewModel.onLongClickHistoryItem(barcode)
            true
        }
    }
}
