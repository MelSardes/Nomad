@file:OptIn(ExperimentalMaterial3Api::class)

package com.mukite.nomad.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.mukite.nomad.R
import com.mukite.nomad.SplashScreen
import com.mukite.nomad.ui.booking_details.BookingDetails

enum class NomadScreen(@StringRes val title: Int) {
    Splash(title = R.string.splash),
    Home(title = R.string.app_name),
    HotelDetails(title = R.string.hotel),
    BookingDetails(title = R.string.reservation_details),
    Payment(title = R.string.payment),
    UserProfile(title = R.string.profile),
    Bookings(title = R.string.reservations),
    SelectDate(title = R.string.select_date),
}

enum class BottomNavigationItem(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    Home(
        NomadScreen.Home.name,
        R.string.home,
        Icons.Rounded.Home
    ),

    Bookings(
        NomadScreen.Bookings.name,
        R.string.reservations,
        Icons.Rounded.List
    ),

    UserProfile(
        NomadScreen.UserProfile.name,
        R.string.profile,
        Icons.Rounded.Person
    )
}

val bottomNavigationItems = listOf(
    BottomNavigationItem.Home,
    BottomNavigationItem.Bookings,
    BottomNavigationItem.UserProfile
)

@Composable
fun NomadBottomNavigationBar(
    currentDestination: NavDestination?,
    navigateToPage: (String) -> Unit
) {
    NavigationBar() {
        bottomNavigationItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = {
                    Text(
                        stringResource(screen.resourceId),
                        fontWeight = FontWeight.SemiBold,
//                        color = MaterialTheme.colorScheme.primary
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { navigateToPage(screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NomadAppBar(
    currentScreen: NavDestination?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen?.route.orEmpty()) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun NomadApp(
) {
    val navController: NavHostController = rememberNavController()
    val shouldShowBottomBar = remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    Scaffold(

//                TODO : MAKE TOP BAR VISIBLE ONLY WHEN MAIN SCREEN


/*
        topBar = {
                NomadAppBar(
                    currentScreen = currentDestination,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )

            //                    else null
        },
*/


        bottomBar = {

            if (shouldShowBottomBar.value) {

                NomadBottomNavigationBar(
                    currentDestination = currentDestination,
                    navigateToPage = { route ->
                        navController.navigate(route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    })
            }
        }

    ) { innerPadding ->

        NomadNavContent(navController, innerPadding)

        LaunchedEffect(navController) {
            // Update shouldShowBottomBar based on navigation events
            navController.addOnDestinationChangedListener { _, destination, _ ->
                shouldShowBottomBar.value = destination.hierarchy.any {
                    it.route in bottomNavigationItems.map { item -> item.route }
                } == true
            }
        }
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeNomadAppBar(
    currentScreen: NavDestination?,
    canNavigateBack: Boolean,
    navigateUp: () -> Boolean
) {
    TopAppBar(
        title = { Text(
            currentScreen?.route.orEmpty()
        ) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    )
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NomadNavContent(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    val viewModel = remember { NomadViewModel() }
    val appBarScrollState = rememberTopAppBarState()
    val listScrollState = rememberScrollState()

    NavHost(
        navController = navController,
        startDestination = NomadScreen.Splash.name,
        modifier = Modifier.padding(innerPadding),
//        enterTransition = { fadeIn(animationSpec = tween(700)) },
//        exitTransition = { fadeOut(animationSpec = tween(700)) },
//        popEnterTransition = { fadeIn(animationSpec = tween(700)) },
//        popExitTransition = { fadeOut(animationSpec = tween(700)) },
    ) {

        composable(route = NomadScreen.Splash.name) {
            SplashScreen() {
                navController.navigate(NomadScreen.Home.name){
                    launchSingleTop = true
                    navController.popBackStack(NomadScreen.Splash.name, inclusive = true)
                }
            }
        }

        composable(route = BottomNavigationItem.Home.route) {
//                Text(text = "Home")
            HomeScreen(modifier = Modifier.fillMaxSize()) {
                navController.navigate(NomadScreen.BookingDetails.name)
            }
        }

        composable(route = BottomNavigationItem.UserProfile.route) {
            UserProfile(appBarScrollState, listScrollState, Modifier.fillMaxSize())
        }

        composable(route = BottomNavigationItem.Bookings.route) {
            Surface {
                BookingHistory(modifier = Modifier.fillMaxSize())
            }
        }

        composable(route = NomadScreen.SelectDate.name) {
            SelectDateScreen(
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize(),
                onClose = { navController.navigateUp() },
            ) {
                navController.navigate(NomadScreen.BookingDetails.name)
            }
        }

        composable(route = NomadScreen.BookingDetails.name) {
            BookingDetails(viewModel = viewModel, modifier = Modifier.fillMaxSize()) {
                navController.navigateUp()
            }
        }
    }
}

