package nl.hva.level4example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.hva.level4example.data.ShoppingListItem
import nl.hva.level4example.repository.ShoppingListRepository

class ShoppingListViewModel (application: Application) : AndroidViewModel(application) {

    private val _ioScope = CoroutineScope(Dispatchers.IO)
    private val _shoppingListRepository = ShoppingListRepository(application.applicationContext)

    val shoppingListItems: LiveData<List<ShoppingListItem>> = _shoppingListRepository.getAllShoppingListItems()

    fun insertShoppingListItem(shoppingListItem: ShoppingListItem) {
        _ioScope.launch {
            _shoppingListRepository.insertShoppingListItem(shoppingListItem)
        }
    }

    fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) {
        _ioScope.launch {
            _shoppingListRepository.deleteShoppingListItem(shoppingListItem)
        }
    }

    fun deleteAllShoppingListItems() {
        _ioScope.launch {
            _shoppingListRepository.deleteAllShoppingListItems()
        }
    }

    fun updateShoppingListItem(shoppingListItem: ShoppingListItem) {
        _ioScope.launch {
            _shoppingListRepository.updateShoppingListItem(shoppingListItem)
        }
    }

}