package com.crecardbissnes.ebissnescard.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crecardbissnes.ebissnescard.domain.PDFManager
import com.crecardbissnes.ebissnescard.domain.models.BusinessCardModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val pdfManager: PDFManager) : ViewModel() {

    private val mutableCardData = MutableLiveData<BusinessCardModel>()
    val cardData: LiveData<BusinessCardModel>
        get() = mutableCardData

    fun setNewCardModel(newModel: BusinessCardModel) {
        mutableCardData.value = newModel
    }

    private val mutablePDFInfo = MutableLiveData<String>()
    val pdfInfo: LiveData<String>
        get() = mutablePDFInfo

    fun createPDFDocument(name: String, model: BusinessCardModel) {
        viewModelScope.launch {
            pdfManager.createPDF(name, model) {
                mutablePDFInfo.postValue(it)
            }
        }
    }

    fun openPDFDocument(name: String) {
        pdfManager.openPDF(name)
    }

}