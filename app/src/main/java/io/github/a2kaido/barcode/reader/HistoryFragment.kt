package io.github.a2kaido.barcode.reader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.github.a2kaido.barcode.reader.domain.model.EMVCoBarcode
import io.github.a2kaido.barcode.reader.domain.model.RawDataBarcode
import io.github.a2kaido.barcode.reader.domain.model.UrlBarcode
import io.github.a2kaido.barcode.reader.domain.model.WifiBarcode
import io.github.a2kaido.emvco.parseEMVCoText
import kotlinx.android.synthetic.main.bottom_sheet_dialog_barcode.*
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.scan_history_title)

        history_recycler_view.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.barcodeList.observe(this, Observer { barcodeList ->
            if (barcodeList.isEmpty()) {
                history_recycler_view.visibility = View.GONE
                empty_view.visibility = View.VISIBLE
                return@Observer
            }

            history_recycler_view.visibility = View.VISIBLE
            empty_view.visibility = View.GONE
            val adapter = GroupAdapter<ViewHolder>()
            adapter.addAll(barcodeList.asSequence().map {
                BarcodeItem(viewModel, it)
            }.toList())
            history_recycler_view.adapter = adapter
        })

        viewModel.onClickHistoryItemEvent.observe(this, Observer {
            it?.consume()?.let { barcode ->
                val dialog = BottomSheetDialog(requireContext()).apply {
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
                    bottom_sheet_negative_button.setOnClickListener {
                        dismiss()
                    }
                }
                dialog.show()
            }
        })

        viewModel.onLongClickHistoryItemEvent.observe(this, Observer {
            it?.consume()?.let { barcode ->
                AlertDialog.Builder(requireContext())
                    .setMessage(R.string.delete_barcode_alert_dialog_message)
                    .setPositiveButton(R.string.delete) { _, _ ->
                        viewModel.deleteBarcode(barcode)
                    }
                    .setNegativeButton(R.string.cancel) { _, _ -> }
                    .show()
            }
        })

//        history_fab.setOnClickListener {
//            BottomSheetDialog(requireContext()).apply {
//                setContentView(R.layout.bottom_sheet_dialog_filter)
//            }.show()
//        }
    }
}
