package com.fausto.designsystem.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenTab(
    modifier: Modifier,
    tabsList: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    PrimaryTabRow(modifier = modifier, selectedTabIndex = selectedTabIndex) {
        tabsList.forEachIndexed { index, tabTitle ->
            Tab(
                selected = selectedTabIndex == index, onClick = {
                    onTabSelected(index)
                }) {
                Text(text = tabTitle)
            }
        }
    }
}