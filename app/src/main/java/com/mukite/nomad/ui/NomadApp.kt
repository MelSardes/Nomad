package com.mukite.nomad.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.mukite.nomad.R
import com.mukite.nomad.ui.booking_details.BookingDetails

enum class NomadScreen(@StringRes val title: Int) {
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
fun LeNomadBottomNavigationBar(
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
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { navigateToPage(screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondary,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondary
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
    val viewModel = remember { NomadViewModel() }
    val navController: NavHostController = rememberNavController()
    val shouldShowBottomBar = remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = remember {
        navBackStackEntry?.destination
    }

    // Remember expensive calculations for efficiency
    val currentDestinationHierarchy by remember(currentDestination) {
        mutableStateOf(currentDestination?.hierarchy)
    }

    val verticalScrollState = rememberScrollState()


    Scaffold(

//                TODO : MAKE TOP BAR VISIBLE ONLY WHEN MAIN SCREEN
        /*

                        topBar = {
        //                    if (currentDestination?.hierarchy?.any { bottomNavigationItems.contains(it.route) } == true)
                            LeNomadAppBar(
                                currentScreen = currentDestination,
                                canNavigateBack = navController.previousBackStackEntry != null,
                                navigateUp = { navController.navigateUp() }
                            )

        //                    else null
                        },
        */


        bottomBar = {

            if (shouldShowBottomBar.value) {

                LeNomadBottomNavigationBar(currentDestination = currentDestination, navigateToPage = {route ->
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


        NomadNavContent(navController, innerPadding, verticalScrollState, viewModel)


        LaunchedEffect(navController) {
            // Update shouldShowBottomBar based on navigation events
            navController.addOnDestinationChangedListener { _, destination, _ ->
                shouldShowBottomBar.value = destination?.hierarchy?.any {
                    it.route in bottomNavigationItems.map { item -> item.route }
                } == true
            }
        }
    }
}

@Composable
private fun NomadNavContent(
    navController: NavHostController,
    innerPadding: PaddingValues,
    verticalScrollState: ScrollState,
    viewModel: NomadViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NomadScreen.Home.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = BottomNavigationItem.Home.route) {
//                Text(text = "Home")
            HomeScreen(
                verticalScrollState,
                modifier = Modifier.fillMaxSize()
            ) {
                navController.navigate(NomadScreen.BookingDetails.name)
            }
        }


        composable(route = BottomNavigationItem.UserProfile.route) {
            Surface {
                Text(text = "User Profile")
            }
        }

        composable(route = BottomNavigationItem.Bookings.route) {
            Surface {
                Text(text = "Bookings")
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

            BookingDetails(viewModel = viewModel)
        }
    }
}

