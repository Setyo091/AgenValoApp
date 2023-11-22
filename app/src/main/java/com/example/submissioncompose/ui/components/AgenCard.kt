package com.example.submissioncompose.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mynavdrawer.R


@Composable
fun AgenCard(
    photo: Int,
    name: String,
    agenClass: String,
    modifier: Modifier = Modifier
)  {
    val padding = 16.dp
    OutlinedCard(
        modifier = modifier
    ) {
        AsyncImage(
            model = photo,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f / 6f)
                .padding(
                    bottom = padding
                )
            )
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
            modifier = Modifier
                .padding(horizontal = padding)
        )
        Spacer(modifier = Modifier)
        Text(
            text = agenClass,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(horizontal = padding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AgenCardPreview() {
    AgenCard(
        name = "Setyo",
        agenClass = "Controller",
        photo = R.drawable.zz,
    )
}