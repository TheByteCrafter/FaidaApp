package com.technologiesiline.faidaapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import com.technologiesiline.faidaapp.models.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE timestamp >= :startOfDay ORDER BY timestamp DESC")
    fun getTodaysTransactions(startOfDay: Long): Flow<List<Transaction>>

    @Insert
    suspend fun addTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

}