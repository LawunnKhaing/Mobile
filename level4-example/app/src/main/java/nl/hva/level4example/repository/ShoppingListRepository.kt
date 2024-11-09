package nl.hva.level4example.repository

import android.content.Context
import androidx.lifecycle.LiveData
import nl.hva.level4example.data.ShoppingListItem
import nl.hva.level4example.db.ShoppingListDao
import nl.hva.level4example.db.ShoppingListDatabase

class ShoppingListRepository(context: Context) {

    private var _shoppingListDao: ShoppingListDao

    init {
        val shoppingListDatabase = ShoppingListDatabase.getDatabase(context)
        _shoppingListDao = shoppingListDatabase.shoppingListDao()
    }

    fun getAllShoppingListItems(): LiveData<List<ShoppingListItem>> {
        return _shoppingListDao.getAllShoppingListItems()
    }

    suspend fun insertShoppingListItem(shoppingListItem: ShoppingListItem) {
        _shoppingListDao.insertShoppingListItem(shoppingListItem)
    }

    suspend fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) {
        _shoppingListDao.deleteShoppingListItem(shoppingListItem)
    }

    suspend fun deleteAllShoppingListItems() {
        _shoppingListDao.deleteAllShoppingListItems()
    }

    suspend fun updateShoppingListItem(shoppingListItem: ShoppingListItem) {
        _shoppingListDao.updateShoppingListItem(shoppingListItem)
    }

}
