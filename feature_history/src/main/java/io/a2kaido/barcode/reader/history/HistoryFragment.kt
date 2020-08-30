package io.a2kaido.barcode.reader.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.a2kaido.barcode.reader.history.databinding.FragmentHistoryBinding
import io.a2kaido.barcode.reader.ui.common.BarcodeBottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.scan_history_title)

        binding.historyRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.barcodeList.observeNonNull(this) { barcodeList ->
            if (barcodeList.isEmpty()) {
                binding.historyRecyclerView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
                return@observeNonNull
            }

            binding.historyRecyclerView.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
            val adapter = GroupAdapter<GroupieViewHolder>()
            adapter.addAll(barcodeList.asSequence().map {
                BarcodeItem(viewModel, it)
            }.toList())
            binding.historyRecyclerView.adapter = adapter
        }

        viewModel.onClickHistoryItemEvent.observeNonNull(this) {
            it.consume()?.let { barcode ->
                BarcodeBottomSheetDialogFragment.newInstance(barcode)
                    .show(parentFragmentManager, null)
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
