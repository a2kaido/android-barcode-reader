package io.github.a2kaido.barcode.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.github.a2kaido.barcode.reader.common.BarcodeBottomSheetDialogFragment
import io.github.a2kaido.barcode.reader.common.observeNonNull
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

        history_recycler_view.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.barcodeList.observeNonNull(this) { barcodeList ->
            if (barcodeList.isEmpty()) {
                history_recycler_view.visibility = View.GONE
                empty_view.visibility = View.VISIBLE
                return@observeNonNull
            }

            history_recycler_view.visibility = View.VISIBLE
            empty_view.visibility = View.GONE
            val adapter = GroupAdapter<ViewHolder>()
            adapter.addAll(barcodeList.asSequence().map {
                BarcodeItem(viewModel, it)
            }.toList())
            history_recycler_view.adapter = adapter
        }

        viewModel.onClickHistoryItemEvent.observeNonNull(this) {
            it.consume()?.let { barcode ->
                BarcodeBottomSheetDialogFragment.newInstance(barcode)
                    .show(requireFragmentManager(), null)
            }
        }

        viewModel.onLongClickHistoryItemEvent.observeNonNull(this) {
            it.consume()?.let { barcode ->
                AlertDialog.Builder(requireContext())
                    .setMessage(R.string.delete_barcode_alert_dialog_message)
                    .setPositiveButton(R.string.delete) { _, _ ->
                        viewModel.deleteBarcode(barcode)
                    }
                    .setNegativeButton(R.string.cancel) { _, _ -> }
                    .show()
            }
        }

//        history_fab.setOnClickListener {
//            BottomSheetDialog(requireContext()).apply {
//                setContentView(R.layout.bottom_sheet_dialog_filter)
//            }.show()
//        }
    }
}
