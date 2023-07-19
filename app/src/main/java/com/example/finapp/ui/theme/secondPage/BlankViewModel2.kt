package com.example.finapp.ui.theme.secondPage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finapp.data.model.Transaction
import com.example.finapp.data.repository.TransactionsRepository

class BlankViewModel2(private val repository: TransactionsRepository) : ViewModel() {
    private val _dataList = MutableLiveData<List<Transaction>>()
    val dataList: LiveData<List<Transaction>> get() = _dataList

    fun getTransactions() {
        repository.getAllTransactions { dataList ->
            _dataList.postValue(dataList)
        }
    }
}