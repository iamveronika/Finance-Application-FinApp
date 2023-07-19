package com.example.finapp.ui.theme.firstPage

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finapp.R
import com.example.finapp.data.model.Category
import com.example.finapp.data.model.TransactionType
import com.example.finapp.data.repository.TransactionsRepository
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var viewModel: BlankViewModel
    private lateinit var pieChartOutcome: PieChart
    private lateinit var pieChartIncome: PieChart
    private lateinit var categoryTextViews: Array<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        pieChartOutcome = view.findViewById(R.id.pieChartOutcome)
        pieChartIncome = view.findViewById(R.id.pieChartIncome)
        categoryTextViews = arrayOf(
            view.findViewById(R.id.textViewRent),
            view.findViewById(R.id.textViewCar),
            view.findViewById(R.id.textViewFood)
        )
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlankViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPieChart(pieChartOutcome)
        setupPieChart(pieChartIncome)
        loadChartData()
    }

    private fun setupPieChart(pieChart: PieChart) {
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.setDragDecelerationFrictionCoef(0.95f)
        pieChart.setDrawHoleEnabled(true)
        pieChart.setBackgroundColor(Color.TRANSPARENT)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.setDrawCenterText(true)
        pieChart.rotationAngle = 0f
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)
    }

    private fun loadChartData() {
        val transactionsRepository = TransactionsRepository()
        transactionsRepository.getAllTransactions { transactionList ->
            val outcomeEntries: ArrayList<PieEntry> = ArrayList()
            val incomeEntries: ArrayList<PieEntry> = ArrayList()

            val outcomeCategoryMap = HashMap<Category, Float>()
            val incomeCategoryMap = HashMap<Category, Float>()

            for (transaction in transactionList) {
                val transactionValue = transaction.value.toFloatOrNull()
                if (transactionValue != null) {
                    val currentCategoryValue = when (transaction.type) {
                        TransactionType.outcome, TransactionType.OUTCOME -> outcomeCategoryMap
                        TransactionType.income, TransactionType.INCOME -> incomeCategoryMap
                        else -> null
                    }

                    if (currentCategoryValue != null) {
                        val currentValue = currentCategoryValue[transaction.category] ?: 0f
                        currentCategoryValue[transaction.category] = currentValue + transactionValue
                    }
                }
            }

            for ((category, value) in outcomeCategoryMap) {
                outcomeEntries.add(PieEntry(value, category.name))
            }

            for ((category, value) in incomeCategoryMap) {
                incomeEntries.add(PieEntry(value, category.name))
            }

            loadChart(pieChartOutcome, outcomeEntries)
            loadChart(pieChartIncome, incomeEntries)
        }
    }

    private fun loadChart(pieChart: PieChart, entries: ArrayList<PieEntry>) {
        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(requireContext(), R.color.purple_200))
        colors.add(ContextCompat.getColor(requireContext(), R.color.yellow))
        colors.add(ContextCompat.getColor(requireContext(), R.color.red))
        colors.add(ContextCompat.getColor(requireContext(), R.color.grey))
        // Add more colors if needed for additional categories

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.highlightValues(null)
        pieChart.invalidate()
    }
}
