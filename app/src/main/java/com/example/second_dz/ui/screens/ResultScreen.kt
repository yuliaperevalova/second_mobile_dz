package com.example.second_dz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.second_dz.ui.viewmodel.Calculation

@Composable
fun ResultScreen(
    calcId: String,
    calculation: Calculation?,
    onBackToEdit: () -> Unit,
    onNewCalculation: () -> Unit,
    onHistoryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 48.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (calculation != null) {
            Text(
                text = "Результат расчёта",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            Text(
                text = "Сумма счёта: ${String.format("%.2f", calculation.billAmount)}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "Количество людей: ${calculation.peopleCount}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "С каждого: ${String.format("%.2f", calculation.perPerson)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        } else {
            Text(
                text = "Расчёт не найден",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
        
        Button(
            onClick = onBackToEdit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Back to edit")
        }
        
        Button(
            onClick = onNewCalculation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("New calculation")
        }
        
        Button(
            onClick = onHistoryClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("History")
        }
    }
}
