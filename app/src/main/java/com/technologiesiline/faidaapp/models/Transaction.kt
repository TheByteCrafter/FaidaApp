package com.technologiesiline.faidaapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val isIncome: Boolean,
    val category: String,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)