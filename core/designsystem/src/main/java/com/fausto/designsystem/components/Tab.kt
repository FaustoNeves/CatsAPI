package com.fausto.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fausto.designsystem.theme.CatsAppTheme

/**
 * @param tabsList a list containing the title of all tabs
 * @param selectedTabIndex index of the selected tab
 * @param onTabSelected it's a callback to pass the index of the current selected tab
 */

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

@Preview(name = "light mode")
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsScreenTabPreview() {
    var selectedTabIndex by remember { mutableIntStateOf(value = 0) }
    CatsAppTheme {
        DetailsScreenTab(
            modifier = Modifier.padding(12.dp),
            tabsList = listOf("Tab 1", "Tab 2"),
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { index ->
                selectedTabIndex = index
            })
    }
}