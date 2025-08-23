package com.fausto.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import com.fausto.model.BreedImageModel
import kotlin.math.absoluteValue

@Composable
fun GalleryHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    breedImages: List<BreedImageModel>
) {
    HorizontalPager(modifier = modifier.fillMaxSize(), state = pagerState) { page ->
        Card(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    alpha = lerp(
                        start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxSize(),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.scrim)) {
            Box {
                AsyncImage(
                    modifier = modifier.fillMaxSize(),
                    model = breedImages[page].url,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

private const val contentDescription = "cat image"