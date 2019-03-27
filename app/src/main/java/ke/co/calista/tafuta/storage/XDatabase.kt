package ke.co.calista.tafuta.storage


import android.content.Context
import android.nfc.Tag


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.kogicodes.sokoni.models.v1.oauth.Profile

import ke.co.calista.tafuta.storage.dao.ProfileDao

@Database(entities = arrayOf(Profile::class), version = 1, exportSchema = false)
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

    abstract fun profileDao(): ProfileDao



}
