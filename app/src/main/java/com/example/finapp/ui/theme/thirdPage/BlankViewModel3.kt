package com.example.finapp.ui.theme.thirdPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finapp.data.model.Category
import com.example.finapp.data.model.Transaction
import com.example.finapp.data.model.TransactionType
import com.example.finapp.data.repository.TransactionsRepository

class BlankViewModel3(private val repository: TransactionsRepository) : ViewModel() {

    private val _currentTransaction = MutableLiveData<Transaction>(Transaction())
    val currentTransaction: LiveData<Transaction> = _currentTransaction

    fun addTransaction() {
        _currentTransaction.value?.let { transaction ->
            transaction.time = System.currentTimeMillis()
            repository.addTransaction(transaction)
            _currentTransaction.value = Transaction()
        }
    }

    fun addNumber(number: String) {
        _currentTransaction.value?.let { transaction ->
            transaction.value += number
            _currentTransaction.value = transaction
        }
    }

    fun addType(type1: TransactionType) {
        _currentTransaction.value?.let { transaction ->
            transaction.type = type1
            _currentTransaction.value = transaction
        }
    }

    fun addCategory(category: Category) {
        _currentTransaction.value?.let { transaction ->
            transaction.category = category
            _currentTransaction.value = transaction
        }
    }

    fun clearLastDigit() {
        _currentTransaction.value?.let { transaction ->
            transaction.value = transaction.value.dropLast(1)
            _currentTransaction.value = transaction
        }
    }
}