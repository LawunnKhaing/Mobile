package com.example.level5example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.level5example.data.api.util.Resource
import com.example.level5example.data.model.Number
import com.example.level5example.respository.NumbersInFirestoreRepository
import com.example.level5example.respository.NumbersRepository
import kotlinx.coroutines.launch

class NumbersViewModel(application: Application) : AndroidViewModel(application) {
    private val _numbersRepository = NumbersRepository()
    private val _numbersInFirestoreRepository = NumbersInFirestoreRepository()

    /**
     *
     * Expose non MutableLiveData via getter
     *
     */
    // For getting data from numbersapi.com
    val numberResource: LiveData<Resource<Number>>
        get() = _numberResource

    //initialize it with an Empty type of Resource
    private val _numberResource: MutableLiveData<Resource<Number>> = MutableLiveData(Resource.Empty())

    // For uploading number data to Firestore.
    val numberToFirestoreResource: LiveData<Resource<String>>
        get() = _numberToFirestoreResource

    //initialize it with an Empty type of Resource
    private val _numberToFirestoreResource: MutableLiveData<Resource<String>> =
        MutableLiveData(Resource.Empty())

    val numbersFromFirestoreResource: LiveData<Resource<List<String>>>
        get() = _numbersFromFirestoreResource
    private val _numbersFromFirestoreResource: MutableLiveData<Resource<List<String>>> =
        MutableLiveData(Resource.Empty())

    /**
     * The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the ViewModel is cleared.
     * Extension method of lifecycle-viewmodel-ktx library
     */
    // Get data from numbersapi.com associated with a random number, generated by this backend server.
    fun getNumber() {
        //set resource type to loading
        _numberResource.value = Resource.Loading()

        viewModelScope.launch {
            _numberResource.value = _numbersRepository.getRandomNumber()
        }
    }

    // Add data to Firestore. Input is a Number instance.
    fun addNumberToFirestore(number: Number) {
        _numberToFirestoreResource.value = Resource.Loading()

        viewModelScope.launch {
            _numberToFirestoreResource.value =
                _numbersInFirestoreRepository.addNumberToFirestore(number)
        }
    }

    fun getHistoryFromFirestore() {
        _numbersFromFirestoreResource.value = Resource.Loading()

        viewModelScope.launch {
            _numbersFromFirestoreResource.value =
                _numbersInFirestoreRepository.getHistoryFromFirestore()
        }
    }

    fun deleteHistory() {
        viewModelScope.launch {
            _numbersInFirestoreRepository.deleteHistory()
        }
    }
}
