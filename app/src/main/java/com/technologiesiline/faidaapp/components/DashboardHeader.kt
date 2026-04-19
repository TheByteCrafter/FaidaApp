package com.technologiesiline.faidaapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DashboardHeader(totalProfit: Double, income: Double, expenses: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)) // Dark base
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF00F2FE).copy(alpha = 0.15f), Color(0xFF4FACFE).copy(alpha = 0.05f))
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text("Net Profit Today", color = Color.Gray, style = MaterialTheme.typography.labelMedium)
                Text(
                    text = "KES ${String.format("%,.2f", totalProfit)}",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    SummaryItem(label = "Income", amount = income, color = Color(0xFF00E676)) // Neon Green
                    SummaryItem(label = "Expenses", amount = expenses, color = Color(0xFFFF5252)) // Neon Red
                }
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, amount: Double, color: Color) {
    Column {
        Text(label, color = Color.Gray, style = MaterialTheme.typography.labelSmall)
        Text("KES ${String.format("%,.0f", amount)}", color = color, fontWeight = FontWeight.SemiBold)
    }
}