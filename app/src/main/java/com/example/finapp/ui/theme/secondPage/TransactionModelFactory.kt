package com.example.finapp.ui.theme.secondPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finapp.data.repository.TransactionsRepository

class TransactionModelFactory(private val repository: TransactionsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlankViewModel2::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BlankViewModel2(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}