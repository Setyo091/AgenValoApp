package com.example.submissioncompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.Repossitory
import com.example.submissioncompose.model.Agen
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repossitory) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Agen>> = MutableStateFlow(UiState.Loading)
    val uiState: MutableStateFlow<UiState<Agen>> get() = _uiState

    fun getMemberById(id: Int) {
        viewModelScope.launch {
            repository.getMemberById(id)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { member ->
                    _uiState.value = UiState.Success(member)
                }
        }
    }
}