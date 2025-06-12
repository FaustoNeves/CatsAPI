package com.fausto.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

/**
 * @param onSearch callback to search breed based on the input
 * @param userInput users search text input
 * @param updateUserInput callback to hold the search text input
 * */

@Composable
fun SearchTextField(
    onSearch: (String) -> Unit,
    userInput: String,
    updateUserInput: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
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