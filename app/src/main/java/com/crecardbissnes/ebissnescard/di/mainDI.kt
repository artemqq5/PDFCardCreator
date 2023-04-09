package com.crecardbissnes.ebissnescard.di

import com.crecardbissnes.ebissnescard.domain.PDFManager
import com.crecardbissnes.ebissnescard.presenter.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainDI = module {

    factory {
        PDFManager(get())
    }

    viewModel {
        MainViewModel(get())
    }

}