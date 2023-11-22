package com.example.submissioncompose.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.submissioncompose.navigation.Screen

data class NavItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)