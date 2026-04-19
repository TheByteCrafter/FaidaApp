package com.technologiesiline.faidaapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technologiesiline.faidaapp.models.Transaction
import com.technologiesiline.faidaapp.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class HomeViewModel(private val repository: TransactionRepository) : ViewModel() {

    val allTransactions: StateFlow<List<Transaction>> = repository.allTransactions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTransaction(amount: Double, category: String, isIncome: Boolean) {
        viewModelScope.launch {
            val newTransaction = Transaction(
                amount = amount,
                category = if (category.isEmpty()) "General" else category,
                isIncome = isIncome,
                timestamp = System.currentTimeMillis()
            )
            repository.insert(newTransaction)

        }
    }
    val uiState = allTransactions.map { transactions ->
        val income = transactions.filter { it.isIncome }.sumOf { it.amount }
        val expenses = transactions.filter { !it.isIncome }.sumOf { it.amount }
        HomeUiState(
            totalProfit = income - expenses,
            income = income,
            expenses = expenses,
            transactions = transactions.take(10)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())
}

data class HomeUiState(
    val totalProfit: Double = 0.0,
    val income: Double = 0.0,
    val expenses: Double = 0.0,
    val transactions: List<Transaction> = emptyList()
)