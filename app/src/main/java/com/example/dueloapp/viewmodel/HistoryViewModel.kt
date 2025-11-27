package com.example.dueloapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers // Importante
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext // Importante

class HistoryViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _history = MutableLiveData<List<GameHistory>>()
    val history: LiveData<List<GameHistory>> = _history

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadHistory() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) { // Ejecutar en hilo de fondo
            try {
                val list = repository.getGameHistory()
                // Volver al hilo principal para actualizar la UI
                withContext(Dispatchers.Main) {
                    _history.value = list
                    _loading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                    // Opcional: Manejar error
                }
            }
        }
    }
}