package com.fausto.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.fausto.designsystem.theme.CatsAppTheme
import com.fausto.model.BreedDetailModel
import com.fausto.model.BreedsModel
import com.fausto.model.WeightModel
import com.fausto.texts.R

/**
 * @param currentSection the first letter of the breed's name, used to group breeds alphabetically, example;
 * the following breeds have currentSection "A": "Abyssinian", "American Shorthair".
 * @param breedsCollection list of breeds to be displayed in the card.
 * @param onItemClick callback to navigate to the breed details screen, passing queryBreedId and referenceImageId.
 * */

@Composable
fun BreedCard(
    modifier: Modifier = Modifier,
    currentSection: String,
    breedsCollection: List<BreedsModel>,
    onItemClick: (queryBreedId: String, referenceImageId: String) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.padding(top = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            modifier = Modifier.padding(all = 8.dp),
            text = currentSection
        )
        HorizontalDivider(modifier = Modifier.padding(bottom = 2.dp), thickness = 2.dp)
        breedsCollection.forEach {
            Text(modifier = modifier
                .padding(all = 8.dp)
                .clickable {
                    onItemClick(it.queryBreedId, it.referenceImageId)
                }, text = it.name
            )
        }
    }
}

@Preview(name = "light mode")
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BreedCardPreview() {
    CatsAppTheme {
        BreedCard(
            currentSection = "A",
            breedsCollection = listOf(
                BreedsModel(
                    section = "A",
                    queryBreedId = "",
                    referenceImageId = "",
                    "Abyssinian"
                ),
                BreedsModel(
                    section = "A",
                    queryBreedId = "",
                    referenceImageId = "",
                    "Aegean"
                ),
                BreedsModel(
                    section = "A",
                    queryBreedId = "",
                    referenceImageId = "",
                    "American Bobtail"
                )
            ),
            onItemClick = { _, _ -> },
        )
    }
}

/**
 * Private text only used in DetailsBreedCard composable
 * */
@Composable
private fun BreedInfoText(
    modifier: Modifier = Modifier, text: String, fontSize: TextUnit = TextUnit.Unspecified
) {
    Text(
        modifier = modifier.padding(horizontal = 6.dp),
        fontSize = fontSize,
        color = Color.White,
        text = text
    )
}

/**
 * @param imageUrl image's url address
 * @param contentDescription accessibility text describing what the image represents
 * @param breedDetailModel this is where we have all details to be displayed: breed's name,
 * origin, lifespan, weight, temperament and description
 * */
@Composable
fun DetailsBreedCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String,
    breedDetailModel: BreedDetailModel
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.scrim)
    ) {
        Box {
            AsyncImage(
                modifier = modifier.fillMaxSize(),
                model = imageUrl,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.scrim.copy(alpha = .6F),
                                MaterialTheme.colorScheme.scrim.copy(alpha = .6F)
                            ),
                        )
                    )
            )
            Column(
                modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BreedInfoText(text = breedDetailModel.name, fontSize = 30.sp)
                BreedInfoText(text = stringResource(id = R.string.origin, breedDetailModel.origin))
                BreedInfoText(
                    text = stringResource(
                        id = R.string.lifespan, breedDetailModel.lifeSpan
                    )
                )
                BreedInfoText(text = stringResource(id = R.string.weight))
                BreedInfoText(
                    text = stringResource(
                        id = R.string.weight_metric, breedDetailModel.weight.metric
                    )
                )
                BreedInfoText(
                    text = stringResource(
                        id = R.string.weight_imperial, breedDetailModel.weight.imperial
                    )
                )
                BreedInfoText(
                    text = stringResource(
                        R.string.temperament,
                        breedDetailModel.temperament
                    )
                )
                BreedInfoText(
                    text = stringResource(
                        R.string.description,
                        breedDetailModel.description
                    )
                )
            }
        }
    }
}

@Preview(name = "light mode")
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsBreedCardPreview() {
    CatsAppTheme {
        DetailsBreedCard(
            modifier = Modifier.padding(12.dp),
            imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
            contentDescription = "description",
            breedDetailModel = BreedDetailModel(
                weight = WeightModel(imperial = "7  -  10", metric = "3 - 5"),
                id = "abys",
                name = "Abyssinian",
                temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                origin = "Egypt",
                description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
                lifeSpan = "14 - 15"
            )
        )
    }
}
