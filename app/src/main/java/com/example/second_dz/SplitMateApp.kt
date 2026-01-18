package com.example.second_dz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.second_dz.ui.screens.HistoryScreen
import com.example.second_dz.ui.screens.HomeScreen
import com.example.second_dz.ui.screens.InputScreen
import com.example.second_dz.ui.screens.ResultScreen
import com.example.second_dz.ui.viewmodel.SplitViewModel

@Composable
fun SplitMateApp() {
    val navController = rememberNavController()
    val viewModel: SplitViewModel = viewModel()
    val calculations by viewModel.calculations.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStartClick = {
                    navController.navigate("input")
                },
                onHistoryClick = {
                    navController.navigate("history")
                }
            )
        }
        composable("input") {
            InputScreen(
                viewModel = viewModel,
                onCalculateClick = { calcId ->
                    navController.navigate("result/$calcId")
                },
                onHistoryClick = {
                    navController.navigate("history")
                }
            )
        }
        composable("result/{calcId}") { backStackEntry ->
            val calcId = backStackEntry.arguments?.getString("calcId") ?: ""
            val calculation = viewModel.getCalculationById(calcId)
            ResultScreen(
                calcId = calcId,
                calculation = calculation,
                onBackToEdit = {
                    navController.navigate("input") {
                        popUpTo("home") { inclusive = false }
                    }
                },
                onNewCalculation = {
                    viewModel.clearInputValues()
                    navController.navigate("input") {
                        popUpTo("home") { inclusive = false }
                    }
                },
                onHistoryClick = {
                    navController.navigate("history")
                }
            )
        }
        composable("history") {
            HistoryScreen(
                calculations = calculations,
                onCalculationClick = { calcId ->
                    navController.navigate("result/$calcId")
                },
                onHomeClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
