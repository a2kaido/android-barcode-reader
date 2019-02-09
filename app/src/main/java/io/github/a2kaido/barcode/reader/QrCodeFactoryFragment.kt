package io.github.a2kaido.barcode.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_qr_code_factory.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class QrCodeFactoryFragment : Fragment() {

    private val viewModel: QrCodeFactoryViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qr_code_factory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        requireNotNull(args)
        code.text = QrCodeFactoryFragmentArgs.fromBundle(args).value

        viewModel.barcode.observe(this, Observer {

        })
    }
}