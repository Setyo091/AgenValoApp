@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.submissioncompose

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.submissioncompose.model.NavItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainState(
    val drawerState: DrawerState,
    private val scope: CoroutineScope,
    val snackbarHostState: SnackbarHostState,
) {
    fun onMenuClick() {
        scope.launch {
            if (drawerState.isClosed) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }
    fun onItemSelected(item: NavItem) {
        scope.launch {
            drawerState.close()
        }
    }
    fun onBackPress() {
        if (drawerState.isOpen) {
            scope.launch {
                drawerState.close()
            }
        }
    }
}

@Composable
fun rememberMyNavDrawerState(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    coroutinesScope: CoroutineScope = rememberCoroutineScope(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
): MainState =
    remember(drawerState, coroutinesScope, snackbarHostState) {
      MainState(drawerState, coroutinesScope, snackbarHostState)
    }