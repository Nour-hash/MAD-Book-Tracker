package com.example.booktrackerapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booktrackerapp.api.BookItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor() : ViewModel(){

    private val _favoriteBooks = MutableLiveData<List<BookItem>>(emptyList())
    val favoriteBooks: LiveData<List<BookItem>> get() = _favoriteBooks

    fun addFavoriteBook(book: BookItem) {
        val currentFavorites = _favoriteBooks.value?.toMutableList() ?: mutableListOf()
        currentFavorites.add(book)
        _favoriteBooks.value = currentFavorites
    }

    fun removeFavoriteBook(book: BookItem) {
        val currentFavorites = _favoriteBooks.value?.toMutableList() ?: mutableListOf()
        currentFavorites.remove(book)
        _favoriteBooks.value = currentFavorites
    }
}