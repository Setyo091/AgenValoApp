package com.example.submissioncompose.ui.screen.home



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissioncompose.ViewModelFactory
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.model.Agen
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.components.AgenCard
import com.example.submissioncompose.ui.components.SearchBar
import com.example.submissioncompose.ui.theme.MyNavDrawerTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (id: Int) -> Unit
) {
    val query by viewModel.query

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllAgen()
            }
            is UiState.Success -> {
                Column(
                    modifier = modifier
                ) {
                    SearchBar(
                        query = query,
                        onQueryChange = viewModel::searchFilms,
                        modifier = Modifier.fillMaxWidth()
                    )
                    HomeContent(
                        agens = uiState.data,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    agens: List<Agen>,
    modifier: Modifier = Modifier,
    navigateToDetail: (id: Int) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) { items(agens, key = { it.id}) { agen ->
        AgenCard(photo = agen.photo,
            name = agen.name,
            agenClass = agen.agenclass,
            modifier = Modifier.clickable {
                navigateToDetail(agen.id)
            })
    }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    MyNavDrawerTheme {
        HomeScreen(navigateToDetail = {})
    }
}