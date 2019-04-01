package ke.co.calista.tafuta.storage


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ke.co.calista.tafuta.model.Converters
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.storage.dao.LoginDataDao

@Database(entities = arrayOf(LoginData::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class XDatabase : RoomDatabase() {


    companion object {

        private lateinit var INSTANCE: XDatabase

        fun getDatabase(context: Context): XDatabase? {


            synchronized(XDatabase::class.java) {

                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        XDatabase::class.java, "tafuta_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()


            }
            return INSTANCE
        }
    }

    abstract fun loginDataDao(): LoginDataDao



}
