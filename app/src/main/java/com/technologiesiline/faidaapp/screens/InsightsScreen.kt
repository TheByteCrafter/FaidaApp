package com.technologiesiline.faidaapp.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.technologiesiline.faidaapp.viewModels.HomeViewModel

@Composable
fun InsightsScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    // Calculations
    val categoryTotals = remember(state.transactions) {
        state.transactions
            .filter { !it.isIncome }
            .groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }
            .toList()
            .sortedByDescending { it.second }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Financial Insights",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {


            item {
                SummaryCard(
                    income = state.income,
                    expenses = state.expenses
                )
            }

            // Category Breakdown Section
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PieChart, contentDescription = null, tint = Color(0xFF00F2FE))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Spending by Category",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (categoryTotals.isEmpty()) {
                item {
                    Text(
                        "No expenses recorded yet.",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            } else {
                items(categoryTotals) { (category, total) ->
                    CategoryInsightItem(
                        category = category,
                        amount = total,
                        percentage = if (state.expenses > 0) (total / state.expenses).toFloat() else 0f
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryCard(income: Double, expenses: Double) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(20.dp)
    ) {
        Column {
            Text("Cash Flow", color = Color.Gray, style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                InsightMetric(
                    label = "Total Income",
                    value = "KES $income",
                    icon = Icons.Default.TrendingUp,
                    color = Color(0xFF00E676)
                )
                InsightMetric(
                    label = "Total Expense",
                    value = "KES $expenses",
                    icon = Icons.Default.TrendingDown,
                    color = Color(0xFFFF5252)
                )
            }
        }
    }
}

@Composable
fun InsightMetric(label: String, value: String, icon: ImageVector, color: Color) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text(label, color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.bodySmall)
        }
        Text(value, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun CategoryInsightItem(category: String, amount: Double, percentage: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(category, color = Color.White, fontWeight = FontWeight.Medium)
            Text("KES $amount", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))

        
        LinearProgressIndicator(
            progress = { percentage },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF00F2FE),
            trackColor = Color.White.copy(alpha = 0.1f),
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "${(percentage * 100).toInt()}% of total spending",
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}