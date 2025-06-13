package com.fausto.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fausto.designsystem.theme.CatsAppTheme

/**
 * @param onSearch callback to search breed based on the input
 * @param userInput users search text input
 * @param updateUserInput callback to hold the search text input
 * */

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    userInput: String,
    updateUserInput: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = userInput,
        onValueChange = {
            updateUserInput(it)
            onSearch.invoke(it)
        },
        label = { Text(stringResource(com.fausto.texts.R.string.text_field_component_label)) },
        placeholder = { Text(stringResource(com.fausto.texts.R.string.text_field_component_placeholder)) },
        singleLine = true,
        maxLines = 1,
    )
}

@Preview(name = "light mode")
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchTextFieldPreview() {
    CatsAppTheme {
        SearchTextField(
            onSearch = { _ -> },
            userInput = "",
            updateUserInput = { _ -> }
        )
    }
}