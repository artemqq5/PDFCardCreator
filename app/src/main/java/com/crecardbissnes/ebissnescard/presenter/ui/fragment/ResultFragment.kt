package com.crecardbissnes.ebissnescard.presenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.crecardbissnes.ebissnescard.databinding.FragmentResultBinding
import com.crecardbissnes.ebissnescard.presenter.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewButton.setOnClickListener {
            arguments?.let {
                it.getString("namePDF")?.let { fileName ->
                    mainViewModel.openPDFDocument(fileName)
                }
            }
        }

    }

}