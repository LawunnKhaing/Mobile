package com.example.level3example.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class RemindersViewModel : ViewModel() {
    private val _reminders: MutableList<String> = mutableStateListOf()
    /**
     * Add new reminder to the existing list.
     */
    fun addReminder(newReminder: String) {
        _reminders.add(newReminder)
    }

    // Get the reminders specofied so far.
    fun readReminders() : List<String> {
        return _reminders
    }
}