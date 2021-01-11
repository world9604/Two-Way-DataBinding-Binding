package com.example.twowaydatabindingtest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MakeChatRoomViewModel: ViewModel() {

    val _testText = MutableLiveData<String>()
    val testText: LiveData<String>
        get() = _testText

    val _isAllowedSearch = MutableLiveData<Boolean>()
    val isAllowedSearch: LiveData<Boolean>
        get() = _isAllowedSearch

    fun onStart() {
        _isAllowedSearch.value = true
    }
}