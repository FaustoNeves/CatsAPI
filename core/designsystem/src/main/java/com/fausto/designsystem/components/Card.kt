package com.fausto.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fausto.designsystem.theme.CatsAppTheme
import com.fausto.model.BreedsModel

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