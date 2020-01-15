package com.example.mininotes.ui.archive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArchiveViewModel : ViewModel() {


    private val _text = MutableList<String>().apply{
        value = "This is archive Fragment"
    }
    val text: LiveData<String> = _text

}
