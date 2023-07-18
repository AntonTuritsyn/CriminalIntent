package com.android.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeListViewModel"

class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()

/*      StateFlow — это специализированная версия Flow, разработанная специально для совместного использования состояния приложения.
        StateFlow всегда имеет значение, которое наблюдатели могут собирать из его потока.
        Он начинается с начального значения и кэширует последнее значение, которое было выдано в поток.
        Является идеальным дополнением к классу ViewModel, так как StateFlow всегда будет иметь значение, которое можно предоставить фрагментам и действиям по мере их повторного создания.*/
    private val _crimes: MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())
    val crimes: StateFlow<List<Crime>>
        get() = _crimes.asStateFlow()

    init {
//      Запуск сопрограммы с помощью свойства viewModelScope (оборачиваем инициализацию списка преступлений в новую сопрограмму) ДАЛЕЕ ИЗМЕНЯЕТСЯ!!!!
        /*      Удаляется loadCrimes() и вместо этого предоставляется поток преступлений эффективным способом для своих потребителей*/
        viewModelScope.launch {
            crimeRepository.getCrimes().collect {
                _crimes.value = it
            }
        }
    }

    /*//      Создание приостанавливающей функции (suspend fun)
    suspend fun loadCrimes(): List<Crime> {
        *//*val result = mutableListOf<Crime>()
        delay(5000)
        for (i in 0 until 100) {
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Crime #$i",
                date = Date(),
                isSolved = i % 2 == 0
            )
            result += crime
        }
        return result*//*
        return crimeRepository.getCrimes()
    }*/
}