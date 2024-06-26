package com.example.booktrackerapp.navigation

import PreviewScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booktrackerapp.model.service.ImageUri
import com.example.booktrackerapp.screens.CameraScreen
import androidx.navigation.navArgument
import com.example.booktrackerapp.screens.DetailScreen
import com.example.booktrackerapp.screens.HomeScreen
import com.example.booktrackerapp.screens.LibraryScreen
import com.example.booktrackerapp.screens.SignInScreen
import com.example.booktrackerapp.screens.SignUpScreen
import com.example.booktrackerapp.screens.SplashScreen
import com.example.booktrackerapp.screens.Userscreen
import com.example.booktrackerapp.viewModel.CameraViewModel
import com.example.booktrackerapp.viewModel.HomeViewModel
import com.example.booktrackerapp.viewModel.LibraryViewModel

// Verwaltet alle Navigationspfade.
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: HomeViewModel = viewModel()
    val cameraViewModel: CameraViewModel = viewModel()
    val imageUriHolder = ImageUri()
    val libraryViewModel: LibraryViewModel = viewModel()


    // Definiert den Navigations-Host, der als Kontainer für die Navigationsscreens dient.
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        // Definiert verschiedene Routen und die zugehörigen Composables für die Navigation.
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController,viewModel )
        }
        composable(Screen.LibraryScreen.route) {
            LibraryScreen(navController = navController,libraryViewModel=libraryViewModel)
        }
        composable(Screen.UserScreen.route)
        {
            Userscreen(navController = navController,viewModel)
        }

        composable(Screen.SignInScreen.route) {
            SignInScreen(navController = navController)
        }
        composable(Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.CameraScreen.route)
        {
            CameraScreen(navController = navController, cameraViewModel = cameraViewModel, imageUriHolder = imageUriHolder)
        }
        composable(Screen.PreviewScreen.route)
        {
            PreviewScreen(navController = navController, imageUriHolder = imageUriHolder)
        }
        composable(
            route = Screen.DetailScreen.route + "/{isbn}",
            arguments = listOf(navArgument("isbn") { type = NavType.StringType })
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                isbn = backStackEntry.arguments?.getString("isbn") ?: "", // um die isbn vom backStack zu extrahieren
                libraryId = "defaultLibrary"
            )
        }
    }
}
