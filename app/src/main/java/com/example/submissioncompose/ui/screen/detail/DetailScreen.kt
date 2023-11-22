package com.example.submissioncompose.ui.screen.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mynavdrawer.R
import com.example.submissioncompose.ViewModelFactory
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.theme.MyNavDrawerTheme
import java.util.jar.Attributes.Name

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    agenId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
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
                DetailContent(
                    name = name,
                    description = description,
                    agenclass = agenclass,
                    country = country,
                    photo = photo,
                    modifier = modifier
                )
            }

            is UiState.Error -> {}
        }
    }
}
@Composable
fun DetailContent(
     name: String,
     description: String,
     agenclass : String,
     country : String,
     photo: Int,
     modifier: Modifier = Modifier,
) {
    val textStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold
    )
    Column(modifier = Modifier
        .padding(16.dp)
    ) {
//        Text(
//            text = stringResource(id = Agen Valorant)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    MyNavDrawerTheme() {
        DetailContent(
            name = "setyo",
            description = "adalah seorang gembala",
            agenclass = "Duelist",
            country = "Indonesia",
            photo = R.drawable.zz
        )
    }
    
}
