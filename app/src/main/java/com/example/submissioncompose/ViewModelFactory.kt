package com.example.submissioncompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissioncompose.data.Repossitory
import com.example.submissioncompose.ui.screen.detail.DetailViewModel
import com.example.submissioncompose.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: Repossitory) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository)
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(repository)

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}