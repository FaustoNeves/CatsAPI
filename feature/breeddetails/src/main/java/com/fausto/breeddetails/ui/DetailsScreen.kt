package com.fausto.breeddetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fausto.breeddetails.viewmodel.viewstate.BreedDetailViewState
import com.fausto.designsystem.components.DetailsBreedCard
import com.fausto.designsystem.components.DetailsScreenTab
import com.fausto.designsystem.components.ErrorDialog
import com.fausto.designsystem.components.GalleryHorizontalPager
import com.fausto.designsystem.components.IndeterminateCircularIndicator
import com.fausto.model.BreedImageModel
import com.fausto.model.BreedModel
import com.fausto.texts.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsScreen(
    modifier: Modifier = Modifier,
    breedDetailViewState: BreedDetailViewState,
    getBreedDetail: () -> Unit,
    backButtonAction: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize()
    ) {
        IconButton(onClick = {
            backButtonAction.invoke()
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = iconBackButtonContentDescription
            )
        }

        when (breedDetailViewState) {
            is BreedDetailViewState.Loading -> LoadingState()

            is BreedDetailViewState.InitialState -> {
                getBreedDetail.invoke()
            }

            is BreedDetailViewState.Error -> {
                ErrorState(
                    modifier = modifier.fillMaxSize(),
                    errorMessage = breedDetailViewState.errorMessage,
                    retryAction = { getBreedDetail.invoke() },
                )
            }

            is BreedDetailViewState.Success -> {
                SuccessState(
                    modifier = modifier,
                    breedModel = breedDetailViewState.breed,
                    breedImages = breedDetailViewState.breedImages
                )
            }
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    IndeterminateCircularIndicator(modifier = modifier)
}

@Composable
private fun ErrorState(
    modifier: Modifier = Modifier,
    errorMessage: String,
    retryAction: () -> Unit,
) {
    ErrorDialog(
        modifier = modifier,
        confirmButtonAction = {
            retryAction.invoke()
        },
        errorMessage = errorMessage,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    breedModel: BreedModel,
    breedImages: List<BreedImageModel>
) {
    Column(modifier = modifier) {
        val tabsList = listOf(
            stringResource(R.string.info_tab_title),
            stringResource(R.string.gallery_tab_title)
        )
        var selectedTabIndex by remember { mutableIntStateOf(value = 0) }
        DetailsScreenTab(
            modifier = modifier,
            tabsList = tabsList,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { tabIndexSelected ->
                selectedTabIndex = tabIndexSelected
            })
        Spacer(modifier = Modifier.height(8.dp))
        when (selectedTabIndex) {
            0 -> DisplayDetails(
                modifier = modifier, breedModel = breedModel
            )

            1 -> DisplayGallery(
                modifier = modifier, breedImages = breedImages
            )
        }
    }
}

@Composable
private fun DisplayDetails(modifier: Modifier = Modifier, breedModel: BreedModel) {
    DetailsBreedCard(
        modifier = modifier,
        breedModel.url,
        contentDescription = breedModel.breedDetails.name,
        breedDetailModel = breedModel.breedDetails
    )
}

@Composable
private fun DisplayGallery(modifier: Modifier = Modifier, breedImages: List<BreedImageModel>) {
    val pagerState = rememberPagerState(pageCount = {
        breedImages.size
    })
    GalleryHorizontalPager(modifier = modifier, pagerState, breedImages)
}

const val iconBackButtonContentDescription = "back button"