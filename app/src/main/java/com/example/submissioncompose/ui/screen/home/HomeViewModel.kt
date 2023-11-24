package com.example.submissioncompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.Repossitory
import com.example.submissioncompose.model.Agen
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repossitory
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Agen>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Agen>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllAgen() {
        viewModelScope.launch {
            repository.getAllAgen()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { navArg ->
                    _uiState.value = UiState.Success(navArg)
                }
        }
    }
    fun searchFilms(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.searchAgens(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { agens ->
                    _uiState.value = UiState.Success(agens)
                }
        }
    }
}