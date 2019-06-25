package io.github.a2kaido.barcode.reader.common

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.a2kaido.barcode.reader.R
import io.github.a2kaido.barcode.reader.domain.model.*
import io.github.a2kaido.emvco.parseEMVCoText
import kotlinx.android.synthetic.main.bottom_sheet_dialog_barcode.*

class BarcodeBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private enum class Args {
        BARCODE
    }

    companion object {
        fun newInstance(barcode: BarcodeData) = BarcodeBottomSheetDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Args.BARCODE.name, barcode)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val barcode = requireNotNull(arguments?.getSerializable(Args.BARCODE.name) as? BarcodeData)

        return BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.bottom_sheet_dialog_barcode)

            bottom_sheet_barcode_type.text = when (barcode) {
                is UrlBarcode -> getString(R.string.barcode_type_url)
                is RawDataBarcode -> getString(R.string.barcode_type_raw)
                is WifiBarcode -> getString(R.string.barcode_type_wifi)
                is EMVCoBarcode -> getString(R.string.barcode_type_emvco)
            }
            bottom_sheet_barcode_format.text = barcode.format.name
            bottom_sheet_text.text = barcode.code
            when (barcode) {
                is UrlBarcode -> {
                    bottom_sheet_positive_button.visibility = View.VISIBLE
                    bottom_sheet_positive_button.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(barcode.code)
                        })
                        dismiss()
                    }
                }
                is RawDataBarcode -> {
                    bottom_sheet_positive_button.visibility = View.GONE
                }
                is WifiBarcode -> {
                    bottom_sheet_positive_button.visibility = View.GONE
                }
                is EMVCoBarcode -> {
                    bottom_sheet_positive_button.visibility = View.GONE
                    bottom_sheet_text.text = parseEMVCoText(barcode.code)
                }
            }
            share.setOnClickListener {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, bottom_sheet_text.text.toString())
                sendIntent.type = "text/plain"
                startActivity(Intent.createChooser(sendIntent, "Share Code"))
            }
            bottom_sheet_negative_button.setOnClickListener {
                dismiss()
            }
        }
    }
}