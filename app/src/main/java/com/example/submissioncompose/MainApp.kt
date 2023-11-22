@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.submissioncompose

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.submissioncompose.model.NavItem
import com.example.submissioncompose.navigation.Screen
import com.example.submissioncompose.ui.components.MyTopBar
import com.example.mynavdrawer.R
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.navigation.NavArg
import com.example.submissioncompose.ui.screen.about.AboutScreen
import com.example.submissioncompose.ui.screen.detail.DetailScreen
import com.example.submissioncompose.ui.screen.detail.DetailViewModel
import com.example.submissioncompose.ui.screen.favorite.FavoriteScreen
import com.example.submissioncompose.ui.screen.home.HomeScreen
import com.example.submissioncompose.ui.screen.home.HomeViewModel
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(modifier: Modifier = Modifier,
            navController: NavHostController = rememberNavController()) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val repository = Injection.provideRepository()
    val homeViewModel: HomeViewModel = viewModel(factory = ViewModelFactory(repository))
    val detailViewModel: DetailViewModel = viewModel(factory = ViewModelFactory(repository))

    BackPressHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    val items = listOf(
        NavItem(
            title = stringResource(R.string.home),
            icon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavItem(
            title = stringResource(R.string.favourite),
            icon = Icons.Default.Favorite,
            screen = Screen.Favorite
        ),
        NavItem(
            title = stringResource(R.string.profile),
            icon = Icons.Default.AccountCircle,
            screen = Screen.About
        ),
    )

    val selectedItem = remember { mutableStateOf(items[0]) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if(currentRoute != Screen.Detail.route)
            MyTopBar(
                onMenuClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }
            )
        },
    ) { paddingValues ->
        val navigateToDetail = { id: Int ->
            navController.navigate(Screen.Detail.createRoute(id))
        }
        ModalNavigationDrawer(
            modifier = Modifier.padding(paddingValues),
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(12.dp))
                    items.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.title) },
                            selected = item == selectedItem.value,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    }
                                selectedItem.value = item

                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                    }
                },
                content = {
                    NavHost(navController = navController,
                        startDestination = Screen.Home.route,
                        builder = {
                            composable(Screen.Home.route) {
                                homeViewModel.getAllMember()
                                HomeScreen(
                                    navigateToDetail = navigateToDetail,
                                    viewModel = homeViewModel)
                            }
                            composable(
                                route = Screen.Detail.route,
                                arguments = listOf(navArgument(NavArg.MEMBER_ID.key) { type = NavType.IntType })
                            ) {
                                val agenId = it.arguments?.getInt(NavArg.MEMBER_ID.key) ?: -1
                                DetailScreen(
                                    agenId = agenId,
                                    navigateBack = { navController.navigateUp() })
                            }
                            composable(Screen.Favorite.route) {
                                FavoriteScreen()
                            }

                            composable(Screen.About.route) {
                                AboutScreen()
                            }
                        }
                    )
                }
        )
    }
}




@Composable
fun BackPressHandler(enabled: Boolean = true, onBackPressed: () -> Unit) {
    val currentOnBackPressed by rememberUpdatedState(onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}

