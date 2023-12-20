package com.mukite.nomad.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mukite.nomad.R


@Composable
fun HomeScreen(
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    navigateToDateSelection: () -> Unit,
) {
    var isContentHidden by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .padding(0.dp)
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        HeaderImage()
        Spacer(modifier = Modifier.height(14.dp))

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            HeaderSection()
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = { navigateToDateSelection() }
            ) {
                Text(
                    text = stringResource(R.string.booking_now),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.Black.copy(alpha = 0.2f))

            Spacer(modifier = Modifier.height(24.dp))
            DescriptionSection(isContentHidden = isContentHidden) {
                isContentHidden = !isContentHidden
            }

            Spacer(modifier = Modifier.height(24.dp))
//            GallerySection(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))
            ServicesSection(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))
//            MapSection(modifier = Modifier.fillMaxWidth())



        }
    }
}

@Composable
fun SegmentedSwitch() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp)
                .selectableGroup()
        ) {
            TabItem(
                icon = Icons.Default.Home,
                index = 0,
                selectedTabIndex,
                modifier = Modifier.weight(1f),
                onTabSelected = { selectedTabIndex = it }
            )
            TabItem(
                icon = Icons.Default.Email,
                index = 1,
                selectedTabIndex,
                modifier = Modifier.weight(1f),
                onTabSelected = { selectedTabIndex = it }
            )
            TabItem(
                icon = Icons.Default.Person,
                index = 2,
                selectedTabIndex,
                modifier = Modifier.weight(1f),
                onTabSelected = { selectedTabIndex = it }
            )
            TabItem(
                icon = Icons.Default.Settings,
                index = 3,
                selectedTabIndex,
                modifier = Modifier.weight(1f),
                onTabSelected = { selectedTabIndex = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Selected Tab Index: $selectedTabIndex", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun TabItem(icon: ImageVector, index: Int, selectedIndex: Int, modifier: Modifier = Modifier, onTabSelected: (Int) -> Unit) {
    val selected = selectedIndex == index
    val tint = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = { onTabSelected(index) }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = tint)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Tab $index", color = tint)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberScrollState()) {}
}
