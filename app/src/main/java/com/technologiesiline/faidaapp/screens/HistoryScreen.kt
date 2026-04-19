package com.technologiesiline.faidaapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.technologiesiline.faidaapp.models.Transaction
import com.technologiesiline.faidaapp.viewModels.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val transactions by viewModel.uiState.collectAsState()
    val allTransactions = transactions.transactions

    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }


    val groupedTransactions: Map<String, List<Transaction>> = allTransactions.groupBy {
        dateFormatter.format(Date(it.timestamp))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Transaction History",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 24.dp)
        )


        var searchQuery by remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search transactions...", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF00F2FE)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.White.copy(alpha = 0.05f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                focusedBorderColor = Color(0xFF00F2FE),
                unfocusedBorderColor = Color.White.copy(alpha = 0.1f)
            )
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            groupedTransactions.forEach { (date, dailyTransactions) ->
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0D0D0D))
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = date,
                            color = Color(0xFF00F2FE),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                items(dailyTransactions) { transaction ->
                    HistoryItem(transaction)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun HistoryItem(transaction: Transaction) {
    val timeFormatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val timeString = timeFormatter.format(Date(transaction.timestamp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (transaction.isIncome) Color(0xFF00E676).copy(0.1f) else Color(0xFFFF5252).copy(0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (transaction.isIncome) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                contentDescription = null,
                tint = if (transaction.isIncome) Color(0xFF00E676) else Color(0xFFFF5252),
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
            Text(transaction.category, color = Color.White, fontWeight = FontWeight.Medium)
            Text(timeString, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        }

        Text(
            text = "${if (transaction.isIncome) "+" else "-"} KES ${transaction.amount}",
            color = if (transaction.isIncome) Color(0xFF00E676) else Color(0xFFFF5252),
            fontWeight = FontWeight.Bold
        )
    }
}