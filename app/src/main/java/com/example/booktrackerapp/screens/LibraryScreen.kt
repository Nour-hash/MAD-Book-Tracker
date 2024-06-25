package com.example.booktrackerapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.booktrackerapp.ui.theme.BookTrackerAppTheme
import com.example.booktrackerapp.viewModel.HomeViewModel
import com.example.booktrackerapp.viewModel.LibraryViewModel
import com.example.booktrackerapp.widgets.SimpleBottomAppBar
import com.example.booktrackerapp.widgets.SimpleTopAppBar
import androidx.compose.runtime.livedata.observeAsState
import com.example.booktrackerapp.widgets.BookRowSimple


@Composable
fun LibraryScreen(navController: NavController, libraryViewModel: LibraryViewModel = hiltViewModel()) {
    val favoriteBooks by libraryViewModel.favoriteBooks.observeAsState(emptyList())

    BookTrackerAppTheme {
        Scaffold(
            topBar = { SimpleTopAppBar(navController, title = "Library", backButton = true) },
            bottomBar = { SimpleBottomAppBar(navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn {
                    items(favoriteBooks) { book ->
                        BookRowSimple(book = book, navController = navController, libraryViewModel = libraryViewModel, isClickable = true)
                    }
                }
            }
        }
    }

}