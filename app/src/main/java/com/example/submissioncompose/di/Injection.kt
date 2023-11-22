package com.example.submissioncompose.di

import com.example.submissioncompose.data.Repossitory

object Injection {
    fun provideRepository(): Repossitory {
        return Repossitory.getInstance()
    }
}