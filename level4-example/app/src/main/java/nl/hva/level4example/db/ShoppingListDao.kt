package nl.hva.level4example.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import nl.hva.level4example.data.ShoppingListItem

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shoppingListTable")
    fun getAllShoppingListItems(): LiveData<List<ShoppingListItem>>

    @Insert
    suspend fun insertShoppingListItem(shoppingListItem: ShoppingListItem)

    @Delete
    suspend fun deleteShoppingListItem(shoppingListItem: ShoppingListItem)

    @Query("DELETE FROM shoppingListTable")
    suspend fun deleteAllShoppingListItems()

    @Update
    suspend fun updateShoppingListItem(shoppingListItem: ShoppingListItem)

}
