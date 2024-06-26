package com.example.booktrackerapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.booktrackerapp.viewModel.DetailViewModel
import com.example.booktrackerapp.viewModel.LibraryViewModel
import com.example.booktrackerapp.widgets.BookDetails
import com.example.booktrackerapp.widgets.BookRowSimple
import com.example.booktrackerapp.widgets.ReadStatusButton
import com.example.booktrackerapp.widgets.SimpleTopAppBar

//Bildschirm, der Buchdetails anzeigt, basierend auf einer ISBN.
@Composable
fun DetailScreen(
    navController: NavController,
    isbn: String,
    libraryId:String,
    viewModel: DetailViewModel = hiltViewModel(),
    libraryViewModel: LibraryViewModel = hiltViewModel()
) {
    val bookDetailState = viewModel.bookDetailState
    val errorState = viewModel.errorState
    val isRead = viewModel.readState.value ?: false  // Abrufen des Lesestatus als lokale Variable


    LaunchedEffect(isbn) {
        viewModel.getBookDetails(isbn)
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(navController = navController, title = "Book Details", backButton = true)
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally){
            item { if (errorState.value != null) {
                Text(text = errorState.value ?: "", color = MaterialTheme.colorScheme.error)
            } else {
                bookDetailState.value?.let { book ->
                    BookRowSimple(
                        book = book,
                        navController = navController,
                        isClickable = false
                    ) { bookId, isFavorite ->
                        libraryViewModel.updateFavoriteStatus(bookId, isFavorite)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    BookDetails(Modifier,book = book)
                    Spacer(modifier = Modifier.height(8.dp))
                    ReadStatusButton(isRead = isRead){
                        viewModel.toggleReadStatus(book.volumeInfo.industryIdentifiers?.firstOrNull()?.identifier ?: "")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.saveBook(libraryId,book) }) {
                        Text("Save Book")
                    }
                    Button(onClick = {
                        book.volumeInfo.industryIdentifiers?.firstOrNull()
                            ?.let { libraryViewModel.deleteBook(it.identifier) }
                        navController.popBackStack()  // Navigate back after deletion
                    }) {
                        Text("Delete Book")
                    }
                }
            } }

        }

    }
}