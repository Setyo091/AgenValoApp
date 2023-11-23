package com.example.submissioncompose.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.Repossitory
import com.example.submissioncompose.model.Agen
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: Repossitory
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Agen>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Agen>>>
        get() = _uiState

    fun getFavoriteAgen() {
        viewModelScope.launch {
            repository.getFavoriteAgen()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { films ->
                    _uiState.value = UiState.Success(films)
                }
        }
    }
}