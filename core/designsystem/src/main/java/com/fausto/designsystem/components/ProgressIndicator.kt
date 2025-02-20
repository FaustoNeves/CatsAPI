package com.fausto.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fausto.designsystem.theme.CatsAppTheme

@Composable
fun IndeterminateCircularIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    CircularProgressIndicator(
        modifier = modifier.width(128.dp)
    )
    }
}

@Preview
@Composable
fun IndeterminateCircularIndicatorPreview() {
    CatsAppTheme {
        CircularProgressIndicator()
    }
}
