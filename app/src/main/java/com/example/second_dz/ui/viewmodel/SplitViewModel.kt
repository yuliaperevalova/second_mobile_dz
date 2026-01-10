package com.example.second_dz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Calculation(
    val id: String,
    val billAmount: Double,
    val peopleCount: Int,
    val perPerson: Double
) {
    companion object {
        fun fromId(id: String): Calculation? {
            val parts = id.split("_")
            if (parts.size >= 3) {
                val amount = parts[0].toDoubleOrNull() ?: return null
                val count = parts[1].toIntOrNull() ?: return null
                val perPerson = amount / count
                return Calculation(id, amount, count, perPerson)
            }
            return null
        }
    }
}

class SplitViewModel : ViewModel() {
    private val _calculations = MutableStateFlow<List<Calculation>>(emptyList())
    val calculations: StateFlow<List<Calculation>> = _calculations.asStateFlow()
    
    private val _lastBillAmount = MutableStateFlow<String>("")
    val lastBillAmount: StateFlow<String> = _lastBillAmount.asStateFlow()
    
    private val _lastPeopleCount = MutableStateFlow<String>("")
    val lastPeopleCount: StateFlow<String> = _lastPeopleCount.asStateFlow()
    
    fun addCalculation(calculation: Calculation) {
        viewModelScope.launch {
            val updated = (_calculations.value + calculation).takeLast(5)
            _calculations.value = updated
        }
    }
    
    fun getCalculationById(id: String): Calculation? {
        return _calculations.value.find { it.id == id } 
            ?: Calculation.fromId(id)
    }
    
    fun saveInputValues(billAmount: String, peopleCount: String) {
        _lastBillAmount.value = billAmount
        _lastPeopleCount.value = peopleCount
    }
    
    fun clearInputValues() {
        _lastBillAmount.value = ""
        _lastPeopleCount.value = ""
    }
}
