package com.example.booktrackerapp.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.booktrackerapp.R
import com.example.booktrackerapp.viewModel.HomeViewModel
import com.example.booktrackerapp.api.BookItem
import com.example.booktrackerapp.ui.theme.BookTrackerAppTheme
import com.example.booktrackerapp.widgets.BookListScreen
import com.example.booktrackerapp.widgets.SimpleBottomAppBar
import com.example.booktrackerapp.widgets.SimpleTopAppBar


@Composable
fun HomeScreen(navController: NavController) {
    // State to hold the value of the search query
    val searchTextState = remember { mutableStateOf("") }
    val viewModel: HomeViewModel = viewModel()
    val bookListState = remember { mutableStateOf<List<BookItem>>(emptyList()) }
    val errorState = remember { mutableStateOf("") }
    val isBookListVisible = bookListState.value.isNotEmpty() // Check if book list is not empty

    BookTrackerAppTheme {
        Scaffold(
            topBar = { SimpleTopAppBar(navController, title = "Home", backButton = false) },
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
                // Search bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchTextState.value,
                        onValueChange = { searchTextState.value = it },
                        label = { Text("Search") },
                        placeholder = { Text("Enter ISBN") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),

                        //hier für keyboardActions wurde was gemacht.
                        keyboardActions = KeyboardActions(onDone = {
                            viewModel.searchBookByISBN(
                                searchTextState.value,
                                onSuccess = { books ->
                                    bookListState.value = books
                                    errorState.value = ""
                                },
                                onError = { error ->
                                    errorState.value = error
                                }
                            )
                        })
                    )

                    // Icon to search
                    IconButton(
                        onClick = {
                            //TODO
                            viewModel.searchBookByISBN(
                                searchTextState.value,

                                //hier wurde was gemacht.
                                onSuccess = {books ->
                                    bookListState.value = books
                                    errorState.value = ""
                                },
                                onError = { error ->
                                    errorState.value = error
                                }
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Camera",
                            tint = Color.Black
                        )
                    }
                }

                // Display error messages
                if (errorState.value.isNotEmpty()) {
                    Text(text = errorState.value, color = Color.Red)
                }

                // Display book results using the BookListScreen composable
                // Display book results using the BookListScreen composable if list is not empty
                if (isBookListVisible) {
                    BookListScreen(books = bookListState.value)
                }

                // Button with camera icon
                FloatingActionButton(
                    onClick = {
                        // Handle opening camera
                              //TODO

                        //Firebase.analytics.logEvent("open_camera", null)

                    },
                    modifier = Modifier
                        .padding(100.dp)
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    contentColor = Color.White,
                    containerColor = Color.Black
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera), // Placeholder icon, replace with your camera icon
                        contentDescription = "Camera",
                        modifier = Modifier
                            .size(50.dp)
                    )
                }


            }
        }

    }
}

//fun displayBookDetails(books: List<BookItem>) {

//}


