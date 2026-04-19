package com.technologiesiline.faidaapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.technologiesiline.faidaapp.models.Transaction

@Composable
fun TransactionList(transactions: List<Transaction>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            "Recent Activities",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(transactions) { item ->
                TransactionRow(item)
            }
        }
    }
}

@Composable
fun TransactionRow(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.05f)) // Glass effect
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon based on type
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (transaction.isIncome) Color(0xFF00E676).copy(alpha = 0.2f)
                    else Color(0xFFFF5252).copy(alpha = 0.2f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (transaction.isIncome) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = if (transaction.isIncome) Color(0xFF00E676) else Color(0xFFFF5252),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(transaction.category, color = Color.White, fontWeight = FontWeight.Medium)
            Text("Today", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        }

        Text(
            text = "${if (transaction.isIncome) "+" else "-"} ${transaction.amount}",
            color = if (transaction.isIncome) Color(0xFF00E676) else Color(0xFFFF5252),
            fontWeight = FontWeight.Bold
        )
    }
}