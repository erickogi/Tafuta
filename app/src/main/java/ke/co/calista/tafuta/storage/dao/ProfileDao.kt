package ke.co.calista.tafuta.storage.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.kogicodes.sokoni.models.v1.oauth.Profile

@Dao
interface ProfileDao {


    @Query("SELECT * FROM Profile LIMIT 1 ")
    fun getProfile(): LiveData<Profile>

    @Query("SELECT * FROM Profile LIMIT 1")
    fun fetch(): Profile


    @Insert(onConflict = REPLACE)
    fun insertProfile(model: Profile)


    @Update
    fun updateProfile(model: Profile)

    @Delete
    fun deleteProfile(model: Profile)

    @Query("DELETE FROM Profile")
    fun delete()


}
