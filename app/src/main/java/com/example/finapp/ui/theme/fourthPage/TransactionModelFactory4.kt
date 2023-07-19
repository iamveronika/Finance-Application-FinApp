package com.example.finapp.ui.theme.fourthPage

import BlankViewModel4
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finapp.data.repository.TransactionsRepository

class TransactionModelFactory4(private val repository: TransactionsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlankViewModel4::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BlankViewModel4(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}