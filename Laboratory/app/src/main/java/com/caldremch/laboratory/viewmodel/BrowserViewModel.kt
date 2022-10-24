package com.caldremch.laboratory.viewmodel

import androidx.lifecycle.LiveData
import com.caldremch.laboratory.repository.BrowserRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BrowserViewModel : ViewModel() {
    private val repository by lazy { BrowserRepository() }
    private val _loadDataSuccess = MutableLiveData<Boolean>()
    val loadDataSuccess: LiveData<Boolean>
        get() = _loadDataSuccess
}