package com.example.finapp.ui.theme.thirdPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finapp.data.repository.TransactionsRepository

class TransactionModelFactory(private val repository: TransactionsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlankViewModel3::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BlankViewModel3(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}