package com.example.level5task2.viewmodel

import android.util.Log
import com.example.level5task2.data.model.Movie
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getFavorites(): List<Movie> {
        return try {
            val snapshot = db.collection("favorites").get().await()
            snapshot.documents.mapNotNull { it.toObject(Movie::class.java) }
        } catch (e: Exception) {
            Log.e("FirestoreHelper", "Error fetching favorites: ", e)
            emptyList()
        }
    }

    suspend fun addFavorite(movie: Movie) {
        try {
            val existingMovie = db.collection("favorites")
                .whereEqualTo("id", movie.id)
                .get()
                .await()

            if (existingMovie.isEmpty) {

                db.collection("favorites").add(movie).await()
            }
        } catch (e: Exception) {
            Log.e("FirestoreHelper", "Error adding favorite: ", e)
        }
    }

    suspend fun removeFavorite(movie: Movie) {
        val querySnapshot = db.collection("favorites")
            .whereEqualTo("id", movie.id)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            document.reference.delete().await()
        }
    }
}

