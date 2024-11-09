package com.example.level5task1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level5task1.data.model.Dog
import com.example.level5task1.repository.DogsRepository
import com.example.level5task1.repository.Resource
import kotlinx.coroutines.launch

class DogsViewModel(private val repository: DogsRepository = DogsRepository()) : ViewModel() {
    private val _dogResource = MutableLiveData<Resource<Dog>>(Resource.Empty())
    val dogResource: LiveData<Resource<Dog>> get() = _dogResource

    fun getNewDog() {
        viewModelScope.launch {
            _dogResource.value = Resource.Loading()
            _dogResource.value = repository.getRandomDog()
        }
    }
}
