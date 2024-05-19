package com.example.booktrackerapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.booktrackerapp.api.BookItem

@Composable
fun BookRowSimple(book: BookItem, navController: NavController, isClickable: Boolean = true) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .then(if (isClickable) Modifier.clickable {
                // Navigiere zum DetailScreen mit der ISBN des Buches
                val isbn = book.volumeInfo.industryIdentifiers?.firstOrNull()?.identifier ?: ""
                navController.navigate("detail/$isbn")
            } else Modifier),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = book.volumeInfo.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            BookCardHeader(
                imageUrl = book.volumeInfo.imageLinks?.thumbnail ?: "",
                isFavorite = isFavorite,
                onFavoriteClick = { isFavorite = !isFavorite }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Authors: ${book.volumeInfo.authors.joinToString(", ")}", style = MaterialTheme.typography.bodySmall)
        }
    }
}


@Composable
fun BookCardHeader(imageUrl: String, isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        BookImage(imageUrl = imageUrl)
        FavoriteIcon(isFavorite = isFavorite, onFavoriteClick = onFavoriteClick)
    }
}

@Composable
fun BookImage(imageUrl: String) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl.replace("http://", "https://"))
            .crossfade(true)
            .build(),
        contentDescription = "Book cover",
        contentScale = ContentScale.Fit,
        loading = { CircularProgressIndicator() },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun FavoriteIcon(isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ){
        Icon(
            modifier = Modifier.clickable(onClick = onFavoriteClick),
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Toggle Favorite",
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}

@Composable
fun BookDetails(modifier: Modifier, book: BookItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
            item {
                book.volumeInfo.publisher?.let {
                    DetailText(label = "Publisher:", content = it)
                }
                book.volumeInfo.publishedDate?.let {
                    DetailText(label = "Published Date:", content = it)
                }
                book.volumeInfo.pageCount?.let {
                    DetailText(label = "Page Count:", content = "$it")
                }
                book.volumeInfo.dimensions?.let {
                    DetailText(
                        label = "Dimensions:",
                        content = "${it.height} x ${it.width} x ${it.thickness}"
                    )
                }
                book.volumeInfo.mainCategory?.let {
                    DetailText(label = "Main Category:", content = it)
                }
                book.volumeInfo.averageRating?.let {
                    DetailText(label = "Rating:", content = "$it")
                }
                book.volumeInfo.ratingsCount?.let {
                    DetailText(label = "Ratings Count:", content = "$it")
                }
                book.volumeInfo.retailPrice?.let {
                    DetailText(label = "Price:", content = "${it.amount} ${it.currencyCode}")
                }
                book.volumeInfo.categories?.let { categories ->
                    DetailText(label = "Categories:", content = categories.joinToString(", "))
                }
                book.volumeInfo.description?.let {
                    DetailText(label = "Description:", content = it)
                    Spacer(modifier = Modifier.height(16.dp)) // Optional: Für Abstand am Ende
                }
            }
        }
    }
}

@Composable
fun DetailText(label: String, content: String) {
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(label)
        }
        append(" $content")
    }, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun ReadStatusButton(isRead: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Icon(
            imageVector = if (isRead) Icons.Filled.Check else Icons.Default.Close,
            contentDescription = if (isRead) "Gelesen" else "Nicht gelesen",
            tint = if (isRead) Color.Green else Color.Gray
        )
        Text(text = if (isRead) "Read" else "Marked as not read")
    }
}
