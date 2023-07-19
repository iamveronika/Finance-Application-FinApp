package com.example.finapp.data.repository

import android.util.Log
import com.example.finapp.data.model.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TransactionsRepository {
    fun addTransaction(transaction: Transaction) {
        val db = Firebase.firestore
        db.collection("transactions")
            .add(transaction.toMap())
            .addOnSuccessListener { documentReference ->
                Log.d(">>>>>", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(">>>>>", "Error adding document", e)
            }
    }

    fun getAllTransactions(callback: (List<Transaction>) -> Unit) {
        val db = Firebase.firestore
        val transactions = ArrayList<Transaction>()
        db.collection("transactions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result)
                    transactions.add(Transaction.fromMap(document.data));

                callback(transactions) // Invoke the callback here

            }
            .addOnFailureListener { exception ->
                Log.w(">>>>>", "Error getting documents.", exception)
            }
    }
}