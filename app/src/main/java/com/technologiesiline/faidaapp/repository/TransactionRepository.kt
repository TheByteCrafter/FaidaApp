package com.technologiesiline.faidaapp.repository

import com.technologiesiline.faidaapp.dao.TransactionDao
import com.technologiesiline.faidaapp.models.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        transactionDao.addTransaction(transaction)
    }
}