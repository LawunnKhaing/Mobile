package com.example.level3task2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Portal(val title: String, val url: String)

class PortalViewModel : ViewModel() {
    var portals = mutableStateListOf<Portal>()
    private set

    fun addPortal(name: String, url: String) {
        portals.add(Portal(name, url))
    }
}