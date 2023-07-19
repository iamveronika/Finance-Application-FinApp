import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finapp.data.model.Category
import com.example.finapp.data.model.Transaction
import com.example.finapp.data.repository.TransactionsRepository

class BlankViewModel4(private val transactionsRepository: TransactionsRepository) : ViewModel() {

    private val _dataList = MutableLiveData<List<Transaction>>()
    val dataList: LiveData<List<Transaction>> get() = _dataList

    private var goalAmount: Double = 0.0

    fun setGoalAmount(amount: Double) {
        goalAmount = amount
    }

    fun getGoalAmount(): Double = goalAmount

    fun getTransactions() {
        transactionsRepository.getAllTransactions { transactions ->
            val filteredTransactions = transactions.filter {
                it.category == Category.goal
            }
            _dataList.value = filteredTransactions
        }
    }
}