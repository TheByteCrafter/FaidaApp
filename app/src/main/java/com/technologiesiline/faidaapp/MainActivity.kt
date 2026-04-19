package com.technologiesiline.faidaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.technologiesiline.faidaapp.components.FaidaBottomNav
import com.technologiesiline.faidaapp.repository.TransactionRepository
import com.technologiesiline.faidaapp.screens.HistoryScreen
import com.technologiesiline.faidaapp.screens.InsightsScreen
import com.technologiesiline.faidaapp.screens.MainScreen
import com.technologiesiline.faidaapp.ui.theme.FaidaAppTheme
import com.technologiesiline.faidaapp.utilities.AppDatabase
import com.technologiesiline.faidaapp.viewModels.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = AppDatabase.getDatabase(this)
        val repository = TransactionRepository(database.transactionDao())

        val viewModel = HomeViewModel(repository)

        setContent {
            val navController = rememberNavController()
            val state by viewModel.uiState.collectAsState()

            FaidaAppTheme(darkTheme = true) {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { FaidaBottomNav(navController = navController) }, // Stays visible
                    containerColor = Color(0xFF0D0D0D)
                ) { innerPadding ->


                    NavHost(
                        navController = navController,
                        startDestination = "Home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("Home") {
                            MainScreen(
                                navController = navController,
                                viewModel = viewModel,
                                totalProfit = state.totalProfit,
                                income = state.income,
                                expenses = state.expenses,
                                recentTransactions = state.transactions,
                                modifier = Modifier
                            )
                        }

                        composable("History") {
                            HistoryScreen(viewModel)
                        }

                        composable("Insights") {
                            InsightsScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}

