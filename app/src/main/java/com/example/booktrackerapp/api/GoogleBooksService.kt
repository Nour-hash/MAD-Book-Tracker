package com.example.booktrackerapp.api

import com.example.booktrackerapp.api.GoogleBooksApiClient.service
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksService {
    @GET("volumes")
    suspend fun searchBooksByISBN(@Query("q") isbn: String, @Query("key") apiKey: String): BookResponse{
        return service.searchBooksByISBN("isbn:$isbn", apiKey)
    }
}