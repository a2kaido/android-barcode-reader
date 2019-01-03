package io.github.a2kaido.barcode.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.github.a2kaido.barcode.reader.domain.model.BarcodeFormat
import io.github.a2kaido.barcode.reader.domain.model.UrlBarcode
import kotlinx.android.synthetic.main.fragment_history.*
import java.util.Date

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(BarcodeItem(UrlBarcode("https://www.example.com", BarcodeFormat.CODE_128, Date())))
        adapter.add(BarcodeItem(UrlBarcode("https://www.example.com", BarcodeFormat.CODE_128, Date())))
        adapter.add(BarcodeItem(UrlBarcode("https://www.example.com", BarcodeFormat.CODE_128, Date())))
        adapter.add(BarcodeItem(UrlBarcode("https://www.example.com/aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", BarcodeFormat.CODE_128, Date())))
        adapter.add(BarcodeItem(UrlBarcode("https://www.example.com", BarcodeFormat.CODE_128, Date())))
        adapter.add(BarcodeItem(UrlBarcode("https://www.example.com", BarcodeFormat.CODE_128, Date())))
        adapter.add(BarcodeItem(UrlBarcode("https://www.example.com", BarcodeFormat.CODE_128, Date())))
        adapter.add(BarcodeItem(UrlBarcode("https://www.example.com", BarcodeFormat.CODE_128, Date())))
        history_recycler_view.adapter = adapter
        history_recycler_view.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }
}
