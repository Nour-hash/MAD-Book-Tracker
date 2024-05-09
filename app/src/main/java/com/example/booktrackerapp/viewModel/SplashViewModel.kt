package com.example.booktrackerapp.viewModel

import android.util.Log
import androidx.navigation.NavController
import com.example.booktrackerapp.model.service.AccountService
import com.example.booktrackerapp.navigation.Screen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : BookTrackerViewModel() {

    fun onAppStart(navController: NavController) {

        if (accountService.hasUser()) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(navController.graph.startDestinationId)
            }
        }
        else {
            navController.navigate(Screen.SignInScreen.route) {
                popUpTo(navController.graph.startDestinationId)
            }
        }
    }
}