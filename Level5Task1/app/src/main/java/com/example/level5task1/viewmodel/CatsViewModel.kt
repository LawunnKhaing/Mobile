package com.example.level5task1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level5task1.data.model.Cat
import com.example.level5task1.repository.CatsRepository
import com.example.level5task1.repository.Resource
import kotlinx.coroutines.launch

class CatsViewModel(private val repository: CatsRepository = CatsRepository()) : ViewModel() {
    private val _catResource = MutableLiveData<Resource<Cat>>(Resource.Empty())
    val catResource: LiveData<Resource<Cat>> get() = _catResource

    fun getNewCat() {
        viewModelScope.launch {
            _catResource.value = Resource.Loading()
            _catResource.value = repository.getRandomCat()
        }
    }
}