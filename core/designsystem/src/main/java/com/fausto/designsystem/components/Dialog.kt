package com.fausto.designsystem.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fausto.designsystem.R
import com.fausto.designsystem.theme.CatsAppTheme

/**
 * @param errorId unique string to ensure that a new error dialog is always created
 * @param onDismissRequest called when the user tries to dismiss the Dialog by clicking outside or
 *   pressing the back button. This is not called when the dismiss button is clicked.
 * @param confirmButtonAction button which is meant to confirm a proposed action, thus resolving what
 *   triggered the dialog. The dialog does not set up any events for this button so they need to be
 *   set up by the caller.
 * @param modifier the [Modifier] to be applied to this dialog.
 * @param dismissButtonAction button which is meant to dismiss the dialog. The dialog does not set up any
 *   events for this button so they need to be set up by the caller.
 * @param title title which should specify the purpose of the dialog. The title is not mandatory,
 *   because there may be sufficient information inside the [text].
 * @param errorMessage text which presents the details regarding the dialog's purpose.
 * @param buttonText confirm button's text
 */

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    errorId: String,
    onDismissRequest: (() -> Unit)? = null,
    confirmButtonAction: () -> Unit,
    dismissButtonAction: (() -> Unit)? = null,
    contentDescriptionIcon: String? = null,
    title: String? = null,
    errorMessage: String,
    buttonText: String? = null,
) {
    key(errorId) {
    var openAlertDialog by remember { mutableStateOf(true) }
    if (openAlertDialog) AlertDialog(
        modifier = modifier,
        text = {
            Text(
                text = errorMessage
            )
        },
        onDismissRequest = {
            onDismissRequest?.invoke()
            openAlertDialog = false
        },
        confirmButton = {
            TextButton(onClick = {
                confirmButtonAction()
                openAlertDialog = false
            }, content = {
                Text(
                    text = buttonText ?: stringResource(R.string.try_again_button)
                )
            })
        },
        dismissButton = {
            dismissButtonAction?.let { dismissAction ->
                TextButton(onClick = {
                    dismissAction.invoke()
                    openAlertDialog = false
                }, content = {
                    Text(text = stringResource(R.string.dismiss_button))
                })
                }
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.baseline_error_24),
                contentDescription = contentDescriptionIcon,
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(text = title ?: stringResource(R.string.error_dialog_title))
        },
    )
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    CatsAppTheme {
        ErrorDialog(
            modifier = Modifier,
            errorId = "errorId",
            onDismissRequest = {}, confirmButtonAction = {}, dismissButtonAction = {},
            title = "Error title",
            errorMessage = "Failed to retrieve cats",
            buttonText = "Try again"
        )
    }
}
