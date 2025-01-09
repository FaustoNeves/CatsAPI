package com.fausto.designsystem.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fausto.designsystem.R
import com.fausto.designsystem.theme.CatsAppTheme

@Composable
fun ErrorScreen(
    errorMessage: String,
    retryAction: () -> Unit,
    onDismissRequest: () -> Unit,
    buttonText: String?,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        text = {
            Text(modifier = modifier, text = errorMessage)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(modifier = modifier, onClick = {
                retryAction()
            }) {
                Text(
                    modifier = modifier,
                    text = buttonText ?: stringResource(R.string.try_again_button_text)
                )
            }
        },
    )
}

@Composable
@Preview
fun ErrorScreenPreview() {
    CatsAppTheme {
        ErrorScreen(
            errorMessage = "Error screen",
            retryAction = {},
            onDismissRequest = {},
            buttonText = "Try again"
        )
    }
}
