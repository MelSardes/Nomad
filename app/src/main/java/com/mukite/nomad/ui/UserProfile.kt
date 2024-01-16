package com.mukite.nomad.ui

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mukite.nomad.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    appBarScrollState: TopAppBarState,
    listScrollState: ScrollState,
    modifier: Modifier = Modifier,
    navigateToEmptyState: (title: String) -> Unit
) {

    val activity = (LocalContext.current as? Activity)
    val exitModalShown = remember { mutableStateOf(false) }

    Surface {
        Column(modifier.padding(horizontal = 16.dp)) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.profile),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarScrollState)
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = listScrollState)
            ) {

                ProfileSection(modifier = Modifier)
                Divider(modifier = Modifier.padding(36.dp))

                settingList.forEach {
                    if (it.second == "Se deconnecter") {
                        SettingItem(
                            it.first,
                            it.second,
                            exitColor = MaterialTheme.colorScheme.error
                        ) { exitModalShown.value = true }
                    } else SettingItem(it.first, it.second) { navigateToEmptyState(it.second) }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        AnimatedVisibility(visible = exitModalShown.value) {
            AlertDialog(
                title = {
                    Text(text = "Se deconnecter")
                },
                icon = {
                    Icon(Icons.Filled.ExitToApp, contentDescription = "Exit")
                },
                text = {
                    Text(text = "Voulez-vous vraiment vous deconnecter ?")
                },
                onDismissRequest = { exitModalShown.value = false },
                confirmButton = {
                    Row {
                        TextButton(
                            onClick = { exitModalShown.value = false }
                        ) { Text("Annuler", color = MaterialTheme.colorScheme.secondary) }

                        TextButton(onClick = { activity?.finish() }
                        ) { Text("Se déconnecter") }
                    }
                },
            )
        }

    }
}

val settingList = listOf(
    Icons.Filled.Person to "Modifier le profil",
    Icons.Filled.Notifications to "Notifications",
    Icons.Filled.Help to "Aide",
    Icons.Filled.Warning to "FAQ",
    Icons.Filled.Info to "A propos",
    Icons.Filled.ExitToApp to "Se deconnecter",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    exitColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
    Card(onClick = { onClick() } ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = exitColor)
            Text(
                text = title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleSmall.copy(color = exitColor)
            )
        }
    }
}

@Composable
fun ProfileSection(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfilePhotoSection(modifier = Modifier)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.user_profile_name),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
            )

            Text(
                text = stringResource(R.string.user_profile_email),
                style = MaterialTheme.typography.labelSmall,
                modifier = modifier
            )
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(R.string.user_profile_location),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun ProfilePhotoSection(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .size(120.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.fillMaxSize(0.8f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "UserProfile", showSystemUi = true, device = "id:pixel_7")
@Composable
private fun PreviewUserProfile() {
    Settings(rememberTopAppBarState(), rememberScrollState()) {}
}