package com.example.second_dz.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.second_dz.ui.viewmodel.Calculation

@Composable
fun HistoryScreen(
    calculations: List<Calculation>,
    onCalculationClick: (String) -> Unit,
    onHomeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "История расчётов",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = onHomeClick
            ) {
                Text("Home")
            }
        }
        
        if (calculations.isEmpty()) {
            Text(
                text = "История пуста",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(calculations) { calculation ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCalculationClick(calculation.id)
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${String.format("%.2f", calculation.billAmount)} ₽",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${calculation.peopleCount} чел."
                                )
                            }
                            Text(
                                text = "С каждого: ${String.format("%.2f", calculation.perPerson)} ₽",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
