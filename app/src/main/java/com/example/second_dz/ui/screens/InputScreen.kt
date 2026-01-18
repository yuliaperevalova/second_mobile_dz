package com.example.second_dz.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.second_dz.ui.viewmodel.Calculation
import com.example.second_dz.ui.viewmodel.SplitViewModel

@Composable
fun InputScreen(
    viewModel: SplitViewModel,
    onCalculateClick: (String) -> Unit,
    onHistoryClick: () -> Unit
) {
    val savedBillAmount by viewModel.lastBillAmount.collectAsState()
    val savedPeopleCount by viewModel.lastPeopleCount.collectAsState()
    
    var billAmount by remember { mutableStateOf("") }
    var peopleCount by remember { mutableStateOf("") }
    
    LaunchedEffect(savedBillAmount, savedPeopleCount) {
        billAmount = savedBillAmount
        peopleCount = savedPeopleCount
    }
    
    val isInputValid = billAmount.toDoubleOrNull()?.let { it > 0 } == true && 
                      peopleCount.toIntOrNull()?.let { it > 0 } == true
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 48.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
    ) {
        OutlinedTextField(
            value = billAmount,
            onValueChange = { newValue ->
                billAmount = newValue
                viewModel.saveInputValues(newValue, peopleCount)
            },
            label = { Text("Сумма счёта") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        OutlinedTextField(
            value = peopleCount,
            onValueChange = { newValue ->
                peopleCount = newValue
                viewModel.saveInputValues(billAmount, newValue)
            },
            label = { Text("Количество людей") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        Button(
            onClick = {
                val amount = billAmount.toDoubleOrNull() ?: 0.0
                val count = peopleCount.toIntOrNull() ?: 1
                val calcId = "${amount}_${count}_${System.currentTimeMillis()}"
                val calculation = Calculation(calcId, amount, count, amount / count)
                viewModel.addCalculation(calculation)
                onCalculateClick(calcId)
            },
            enabled = isInputValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Calculate")
        }
        
        Button(
            onClick = onHistoryClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("History")
        }
    }
}
