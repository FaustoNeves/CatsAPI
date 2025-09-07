package com.fausto.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.fausto.designsystem.theme.CatsAppTheme

/**
 * Documentation: [androidx.compose.material3.Snackbar]
 * @param snackbarData data about the current snackbar showing via [SnackbarHostState]
 * @param shape defines the shape of this snackbar's container
 * @param containerColor the color used for the background of this snackbar. Use [Color.Transparent] to have no color
 * @param contentColor the preferred color for content inside this snackbar
 * @param actionColor the color of the snackbar's action
 * @param actionContentColor the preferred content color for the optional action inside this snackbar.
 * @param dismissActionContentColor the preferred content color for the optional dismiss action inside this snackbar
 */

@Composable
fun CatsSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    containerColor: Color = MaterialTheme.colorScheme.inverseSurface,
    contentColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
    actionColor: Color = MaterialTheme.colorScheme.inversePrimary,
    actionContentColor: Color = MaterialTheme.colorScheme.inversePrimary,
    dismissActionContentColor: Color = MaterialTheme.colorScheme.inverseOnSurface
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = dismissActionContentColor
    )
}

@Preview(name = "light mode")
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CatsSnackbarPreview() {
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    CatsAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SnackbarHost(
                modifier = Modifier.align(Alignment.BottomCenter), hostState = snackbarHostState
            ) {
                CatsSnackbar(
                    snackbarData = it, modifier = Modifier
                )
            }
        }
        LaunchedEffect("") {
            snackbarHostState.showSnackbar(
                message = "Snackbar Preview",
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
    }
}