package com.example.submissioncompose.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynavdrawer.R
import com.example.submissioncompose.ViewModelFactory
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.components.DetailTopBar
import com.example.submissioncompose.ui.theme.MyNavDrawerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    agenId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getMemberById(agenId)
            }

            is UiState.Success -> {
                val (
                    _,
                    name,
                    description,
                    agenclass,
                    country,
                    photo,
                ) = uiState.data
                var isFavorite by remember {
                    mutableStateOf(uiState.data.isFavoriteAgen)
                }
                Scaffold(
                    topBar = {
                        DetailTopBar(
                            title = uiState.data.name,
                            isFavorite = isFavorite,
                            onFavoriteClicked = {
                                isFavorite = !isFavorite
                                viewModel.updateFavoritAgen(
                                    uiState.data,
                                    isFavorite
                                )
                            },
                            navigateBack = navigateBack
                        )
                    }
                ) { padding ->
                    Column(modifier = modifier
                        .fillMaxSize()
                        .padding(padding)
                    ) {
                        DetailContent(
                            name = name,
                            description = description,
                            agenclass = agenclass,
                            country = country,
                            photo = photo,
                        )
                    }
                }
            }

            is UiState.Error -> {}
        }
    }
}
@Composable
fun DetailContent(
    name: String,
    description: String,
    agenclass: String,
    country: String,
    photo: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = agenclass,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = country,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color.LightGray))
    }
}


@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    MyNavDrawerTheme() {
        DetailContent(
            name = "setyo",
            description = "adalah seorang gembala yang sedang mengerjakan tugas submission android jetpack compose dan dirumahnya ada banyak orang yang bisa di bilang adalah keluarganya, ada adik, kakak, ayah, ibu, dan saya sendiri.",
            agenclass = "Duelist",
            country = "Indonesia",
            photo = R.drawable.zz,
        )
    }
    
}
