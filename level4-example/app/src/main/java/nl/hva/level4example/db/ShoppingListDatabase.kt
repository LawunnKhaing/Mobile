package nl.hva.level4example.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nl.hva.level4example.data.ShoppingListItem

@Database(entities = [ShoppingListItem::class], version = 1, exportSchema = false)
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        private const val _DATABASE_NAME = "SHOPPINGLIST_DATABASE"

        @Volatile
        private var _INSTANCE: ShoppingListDatabase? = null

        fun getDatabase(context: Context): ShoppingListDatabase {
            val test = 1
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return _INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
//                    context.applicationContext,
                    ShoppingListDatabase::class.java,
                    _DATABASE_NAME
                )
//                instance
                    .allowMainThreadQueries()
                    .build()
                _INSTANCE = instance
                instance
            }
        }
    }
}
