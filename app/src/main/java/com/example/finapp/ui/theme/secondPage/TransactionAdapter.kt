package com.example.finapp.ui.theme.secondPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finapp.R
import com.example.finapp.data.model.Category
import com.example.finapp.data.model.Transaction
import java.text.SimpleDateFormat
import java.util.Date

class TransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImageView: ImageView = itemView.findViewById(R.id.category)
        val transactionNameTextView: TextView = itemView.findViewById(R.id.transactionName)
        val valueTextView: TextView = itemView.findViewById(R.id.value)
        val typeTextView: TextView = itemView.findViewById(R.id.type)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]

        holder.categoryImageView.setImageResource(Category.categoryToResource(transaction.category))
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val netDate = Date(transaction.time)
        holder.transactionNameTextView.text = sdf.format(netDate)
        holder.valueTextView.text = "${transaction.value} Euro"
        holder.typeTextView.text = transaction.type.toString()

    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}
