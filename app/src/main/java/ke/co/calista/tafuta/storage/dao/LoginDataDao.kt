package ke.co.calista.tafuta.storage.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import ke.co.calista.tafuta.model.oauth.LoginData

@Dao
interface LoginDataDao {


    @Query("SELECT * FROM LoginData LIMIT 1 ")
    fun getLoginData(): LiveData<LoginData>

    @Query("SELECT * FROM LoginData LIMIT 1")
    fun fetch(): LoginData


    @Insert(onConflict = REPLACE)
    fun insertLoginData(model: LoginData)


    @Update
    fun updateLoginData(model: LoginData)

    @Delete
    fun deleteLoginData(model: LoginData)

    @Query("DELETE FROM LoginData")
    fun delete()


}
