package com.example.submissioncompose.model

data class Agen(
    val id: Int,
    val name: String,
    val description: String,
    val agenclass : String,
    val country : String,
    val photo: Int,
    val isFavoriteAgen: Boolean,
    )