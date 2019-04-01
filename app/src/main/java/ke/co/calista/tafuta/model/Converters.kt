package ke.co.calista.tafuta.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ke.co.calista.tafuta.model.asset.AssetTypes

import java.lang.reflect.Type
import android.R.attr.data
import com.kogicodes.sokoni.network.RequestService.gson
import java.lang.Exception
import java.util.*
import java.util.Collections.emptyList
import kotlin.collections.ArrayList


class Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<AssetTypes>? {
        try {
            if (value != null && value != "") {
                val listType = object : TypeToken<ArrayList<AssetTypes>>() {

                }.type
                return Gson().fromJson<ArrayList<AssetTypes>>(value, listType)
            } else {
                return ArrayList()
            }
//
//        if (data == null) {
//            return Collections.emptyList<>()
//        }
//        val listType = object : TypeToken<List<MyObject>>() {
//
//        }.type
//        return gson.fromJson(data, listType)
        }catch (e: Exception ){
            e.printStackTrace()
            return ArrayList()
        }
    }

    @TypeConverter
    fun fromArrayLisr(list: ArrayList<AssetTypes>?): String {
        try {
            return if (list != null && list.size > 0) {
                val gson = Gson()
                gson.toJson(list)
            } else {
                ""
            }
        }catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }
}
