package com.example.level5example.respository

import com.example.level5example.data.api.util.Resource
import com.example.level5example.data.model.Number
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class NumbersInFirestoreRepository {
    private val _firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _numbersDocument =
        _firestore.collection("numbers") // Google generated document ID

    suspend fun addNumberToFirestore(number: Number): Resource<String> {
        // Add a new document with a generated id. "number", "type" and "text" are document fields in Firestore.
        val data = hashMapOf(
            "number" to number.number,
            "type" to number.type,
            "text" to number.text,
        )
        try {
            withTimeout(5_000) {
                _numbersDocument
                    .add(data)
                    .await()
            }
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred while uploading number to Firestore.")
        }
        return Resource.Success("Success")
    }

    suspend fun getHistoryFromFirestore(): Resource<List<String>> {
        val historyList = arrayListOf<String>()
        try {
            withTimeout(5_000) {
                _numbersDocument
                    .get().addOnSuccessListener {
                        for (document in it) {
                            val text = document.getString("text")
                            historyList.add(text!!)
                        }
                    }
                    .await()
            }
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred while retrieving number data from Firestore.")
        }
        return Resource.Success(historyList)
    }

    suspend fun deleteHistory(): Resource<String> {
        try {
            withTimeout(5_000) {
                _numbersDocument
                    .get().addOnSuccessListener {
                        for (document in it) {
                            document.reference.delete()
                        }
                    }
                    .await()
            }
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred while deleting history from Firestore.")
        }
        return Resource.Success("Success") // Return an initialized String object.
    }
}

