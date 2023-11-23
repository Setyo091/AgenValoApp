package com.example.submissioncompose.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.submissioncompose.ViewModelFactory
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.model.Agen
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.components.AgenCard

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (id: Int) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteAgen()
            }
            is UiState.Success -> {
                if (uiState.data.isEmpty()) {
                    val composition by rememberLottieComposition(
                        spec = LottieCompositionSpec.Url("https://lottie.host/1992d7a7-d5c2-46e7-9316-baed5fe78c1d/TrtSKsc67V.json")
                    )
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever
                    )
                }
                FavoriteContent(
                    agens = uiState.data,
                    modifier = modifier.testTag("AgenList"),
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}
@Composable
fun  FavoriteContent(
    agens: List<Agen>,
    modifier: Modifier = Modifier,
    navigateToDetail: (id: Int) -> Unit
    ) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("FilmList")
    ) {
        items(agens) {
            AgenCard(
                photo = it.photo,
                name = it.name,
                agenClass = it.agenclass,
                modifier = modifier.clickable { navigateToDetail(it.id) }
            )
        }
    }
}