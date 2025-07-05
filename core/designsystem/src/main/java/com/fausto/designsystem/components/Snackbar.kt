package com.fausto.designsystem.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

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