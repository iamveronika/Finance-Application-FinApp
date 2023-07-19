package com.example.finapp.ui.theme.fourthPage

import BlankViewModel4
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finapp.R
import com.example.finapp.data.model.Category
import com.example.finapp.data.model.TransactionType
import com.example.finapp.data.repository.TransactionsRepository
import com.example.finapp.ui.theme.secondPage.TransactionAdapter

class BlankFragment4 : Fragment() {

    companion object {
        fun newInstance() = BlankFragment4()
    }

    private lateinit var viewModel: BlankViewModel4
    private lateinit var recyclerView: RecyclerView
    private lateinit var goalAmountEditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank4, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = TransactionModelFactory4(TransactionsRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BlankViewModel4::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.transactionsList)
        goalAmountEditText = view.findViewById(R.id.etGoalAmount)
        resultTextView = view.findViewById(R.id.resultTextView)
        val totalGoalsButton: Button = view.findViewById(R.id.totalGoalsButton)

        val llm = LinearLayoutManager(context)
        recyclerView.layoutManager = llm

        goalAmountEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val goalAmount = goalAmountEditText.text.toString().toDoubleOrNull()
                if (goalAmount != null) {
                    viewModel.setGoalAmount(goalAmount)
                    updateResult()
                } else {
                    resultTextView.visibility = View.GONE
                }
                true
            } else {
                false
            }
        }

        viewModel.dataList.observe(viewLifecycleOwner, Observer { dataList ->
            val adapter = TransactionAdapter(dataList)
            recyclerView.adapter = adapter
            updateResult()
        })

        totalGoalsButton.setOnClickListener {
            val dataList = viewModel.dataList.value
            var totalSum = 0.0

            dataList?.forEach { transaction ->
                if (transaction.category == Category.goal) {
                    if (transaction.type == TransactionType.OUTCOME) {
                        totalSum += transaction.value.toDouble()
                    } else if (transaction.type == TransactionType.INCOME) {
                        totalSum -= transaction.value.toDouble()
                    }
                }
            }

            totalGoalsButton.text = String.format("Show  Goals (Piggy Bank Amount: %.2f Euro)", totalSum)
            viewModel.getTransactions()
        }
    }

    private fun updateResult() {
        val dataList = viewModel.dataList.value
        val goalAmount = viewModel.getGoalAmount()

        var sumOfGoals = 0.0

        dataList?.forEach { transaction ->
            try {
                val value = transaction.value.toDouble()
                if (transaction.type == TransactionType.INCOME && transaction.category == Category.goal) {
                    sumOfGoals += value
                } else if (transaction.type == TransactionType.OUTCOME && transaction.category == Category.goal) {
                    sumOfGoals -= value
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }

        if (goalAmount != 0.0) {
            val difference = goalAmount - sumOfGoals
            resultTextView.text = String.format("Total left to save: %.2f Euro", difference)
            resultTextView.visibility = View.VISIBLE
        } else {
            resultTextView.visibility = View.GONE
        }
    }
}