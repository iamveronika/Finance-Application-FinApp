package com.example.finapp.ui.theme.thirdPage

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finapp.R
import com.example.finapp.data.model.Category
import com.example.finapp.data.repository.TransactionsRepository
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    var callback: BottomSheetCallback? = null
    private lateinit var viewModel: BlankViewModel3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = TransactionModelFactory(TransactionsRepository())
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[BlankViewModel3::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentTransaction.observe(viewLifecycleOwner, Observer { transaction ->
            // Update UI based on transaction
        })

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            callback?.onSaveCliecked()
            dismiss()
        }

        val myButton1: ImageButton = view.findViewById(R.id.btn1)
        val myButton2: ImageButton = view.findViewById(R.id.btn2)
        val myButton3: ImageButton = view.findViewById(R.id.btn3)
        val myButton4: ImageButton = view.findViewById(R.id.btn4)
        val myButton5: ImageButton = view.findViewById(R.id.btn5)
        val myButton6: ImageButton = view.findViewById(R.id.btn6)
        val myButton7: ImageButton = view.findViewById(R.id.btn7)
        val myButton8: ImageButton = view.findViewById(R.id.btn8)

        myButton1.setOnClickListener {
            val category1 = Category.sport
            viewModel.addCategory(category1)
            Toast.makeText(context, "Sport is selected", Toast.LENGTH_SHORT).show()
        }
        myButton2.setOnClickListener {
            val category2 = Category.food
            viewModel.addCategory(category2)
            Toast.makeText(context, "Food is selected", Toast.LENGTH_SHORT).show()
        }
        myButton3.setOnClickListener {
            val category3 = Category.auto
            viewModel.addCategory(category3)
            Toast.makeText(context, "Car is selected", Toast.LENGTH_SHORT).show()
        }
        myButton4.setOnClickListener {
            val category4 = Category.house
            viewModel.addCategory(category4)
            Toast.makeText(context, "House is selected", Toast.LENGTH_SHORT).show()
        }
        myButton5.setOnClickListener {
            val category5 = Category.clothes
            viewModel.addCategory(category5)
            Toast.makeText(context, "Clothes is selected", Toast.LENGTH_SHORT).show()
        }
        myButton6.setOnClickListener {
            val category6 = Category.work
            viewModel.addCategory(category6)
            Toast.makeText(context, "Work is selected", Toast.LENGTH_SHORT).show()
        }
        myButton7.setOnClickListener {
            val category7 = Category.goal
            viewModel.addCategory(category7)
            Toast.makeText(context, "Goal is selected", Toast.LENGTH_SHORT).show()
        }
        myButton8.setOnClickListener {
            val category8 = Category.other
            viewModel.addCategory(category8)
            Toast.makeText(context, "Other is selected", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheetDialog = it as BottomSheetDialog
                val bottomSheet =
                    bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?

                val behavior = BottomSheetBehavior.from(bottomSheet!!)
                val layoutParams = bottomSheet.layoutParams

                val windowHeight = 890
                layoutParams?.height = windowHeight

                bottomSheet.layoutParams = layoutParams
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }
}
