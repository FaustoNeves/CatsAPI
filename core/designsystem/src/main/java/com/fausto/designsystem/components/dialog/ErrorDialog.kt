package com.fausto.designsystem.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fausto.designsystem.R
import com.fausto.designsystem.theme.CatsAppTheme

/**
 * @param onDismissRequest called when the user tries to dismiss the Dialog by clicking outside or
 *   pressing the back button. This is not called when the dismiss button is clicked.
 * @param confirmButtonAction button which is meant to confirm a proposed action, thus resolving what
 *   triggered the dialog. The dialog does not set up any events for this button so they need to be
 *   set up by the caller.
 * @param modifier the [Modifier] to be applied to this dialog
 * @param dismissButtonAction button which is meant to dismiss the dialog. The dialog does not set up any
 *   events for this button so they need to be set up by the caller.
 * @param icon optional icon that will appear above the [title] or above the [text], in case a title
 *   was not provided.
 * @param title title which should specify the purpose of the dialog. The title is not mandatory,
 *   because there may be sufficient information inside the [text].
 * @param text text which presents the details regarding the dialog's purpose.
 */

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: (() -> Unit)? = null,
    confirmButtonAction: () -> Unit,
    dismissButtonAction: (() -> Unit)? = null,
    contentDescriptionIcon: String? = null,
    title: String? = null,
    errorMessage: String,
    buttonText: String? = null,
) {
    AlertDialog(
        modifier = modifier,
        text = {
            Text(
                text = errorMessage
            )
        },
        onDismissRequest = {
            onDismissRequest?.invoke()
        },
        confirmButton = {
            TextButton(onClick = {
                confirmButtonAction()
            }, content = {
                Text(
                    text = buttonText ?: stringResource(R.string.try_again_button_text)
                )
            })
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dismissButtonAction?.invoke()
                },
                content = {
                    Text(text = stringResource(R.string.dismiss_button_text))
                }
            )
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.baseline_error_24),
                contentDescription = contentDescriptionIcon,
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            title?.let {
                Text(text = it)
            }
        },
    )
}

@Preview
@Composable
fun ErrorScreenPreview() {
    CatsAppTheme {
        ErrorDialog(
            onDismissRequest = {}, confirmButtonAction = {}, dismissButtonAction = {},
            title = "Error title",
            errorMessage = "Failed to retrieve cats",
            buttonText = "Try again"
        )
    }
}
