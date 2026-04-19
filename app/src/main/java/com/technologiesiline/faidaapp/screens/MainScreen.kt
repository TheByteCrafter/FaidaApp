package com.technologiesiline.faidaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.technologiesiline.faidaapp.components.DashboardHeader
import com.technologiesiline.faidaapp.components.TransactionRow
import com.technologiesiline.faidaapp.models.Transaction
import com.technologiesiline.faidaapp.viewModels.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: HomeViewModel,
    totalProfit: Double,
    income: Double,
    expenses: Double,
    recentTransactions: List<Transaction>,
    navController: NavHostController,
    modifier: Modifier.Companion
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showSheet = true },
                containerColor = Color(0xFF00E676),
                contentColor = Color.Black,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { paddingValues ->
        Column(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp) // Extra padding for FAB/Nav visibility
            ) {
                item {
                    DashboardHeader(
                        totalProfit = totalProfit,
                        income = income,
                        expenses = expenses
                    )
                }

                item {
                    RecentActivityHeader()
                }

                items(recentTransactions) { transaction ->
                    TransactionRow(transaction)
                    Spacer(modifier = modifier.height(8.dp))
                }
            }
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                containerColor = Color(0xFF1A1A1A),
                tonalElevation = 8.dp
            ) {
                AddTransactionForm(
                    onSave = { amount, category, isIncome ->
                        viewModel.addTransaction(amount, category, isIncome)
                        showSheet = false
                    }
                )
            }
        }
    }
}

@Composable
fun AddTransactionForm(onSave: (Double, String, Boolean) -> Unit) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isIncome by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Transaction", color = Color.White, style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .padding(4.dp)
        ) {
            val incomeColor = if (isIncome) Color(0xFF00E676) else Color.Transparent
            val expenseColor = if (!isIncome) Color(0xFFFF5252) else Color.Transparent

            Button(
                onClick = { isIncome = true },
                colors = ButtonDefaults.buttonColors(containerColor = incomeColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) { Text("Income", color = if (isIncome) Color.Black else Color.White) }

            Button(
                onClick = { isIncome = false },
                colors = ButtonDefaults.buttonColors(containerColor = expenseColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) { Text("Expense", color = if (!isIncome) Color.Black else Color.White) }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category (e.g. Food, Rent)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF00F2FE)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (KES)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF00F2FE)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (amount.isNotEmpty()) onSave(amount.toDoubleOrNull() ?: 0.0, category, isIncome)
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00F2FE)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Save Transaction", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun RecentActivityHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Recent Activities", color = Color.White, style = MaterialTheme.typography.titleMedium)
       }
}