package com.fausto.breeddetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import com.fausto.breeddetails.viewmodel.base_info.viewstate.BreedDetailViewState
import com.fausto.designsystem.components.ErrorDialog
import com.fausto.designsystem.components.IndeterminateCircularIndicator
import com.fausto.model.BreedImageModel
import com.fausto.model.BreedModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsScreen(
    modifier: Modifier = Modifier,
    breedDetailViewState: BreedDetailViewState,
    getBreedDetail: () -> Unit,
    backButtonAction: () -> Unit,
) {
    Column(modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
        IconButton(onClick = {
            backButtonAction.invoke()
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "back button"
            )
        }

        when (breedDetailViewState) {
            is BreedDetailViewState.InitialState -> {
                getBreedDetail.invoke()
            }

            is BreedDetailViewState.Loading -> {
                IndeterminateCircularIndicator()
            }

            is BreedDetailViewState.Success -> {
                SuccessState(
                    modifier = Modifier,
                    breedModel = breedDetailViewState.breed,
                    breedImages = breedDetailViewState.breedImages
                )
            }

            is BreedDetailViewState.Error -> {
                ErrorDialog(confirmButtonAction = {
                    getBreedDetail.invoke()
                }, errorMessage = breedDetailViewState.errorMessage)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    breedModel: BreedModel,
    breedImages: List<BreedImageModel>
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Card {
            Box {
                AsyncImage(
                    modifier = modifier.fillMaxWidth(),
                    model = breedModel.url,
                    contentDescription = "cat image",
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent, Color.Black.copy(alpha = 1F)
                                ), startY = 400F
                            )
                        )
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(12.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = breedModel.breeds[0].name
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        val tabTitles = listOf("Details", "Gallery")
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { position, element ->
                Tab(
                    selected = (position == selectedTabIndex),
                    onClick = { selectedTabIndex = position }) {
                    Text(text = element)
                }
            }
        }

        when (selectedTabIndex) {
            0 -> Text("Details selected")
            1 -> DisplayGallery(modifier = modifier, breedImages = breedImages)
        }
    }
}

@Composable
private fun DisplayGallery(modifier: Modifier = Modifier, breedImages: List<BreedImageModel>) {
    val pagerState = rememberPagerState(pageCount = {
        breedImages.size
    })
    HorizontalPager(state = pagerState) { page ->
        Card(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    alpha = lerp(
                        start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()) {
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                model = breedImages[page].url,
                contentDescription = "cat image",
            )
        }
    }
}