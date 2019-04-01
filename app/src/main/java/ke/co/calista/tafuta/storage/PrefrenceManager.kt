package ke.co.calista.tafuta.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.kogicodes.sokoni.models.v1.oauth.Oauth

class PrefrenceManager(internal var _context: Context) {

    internal var pref: SharedPreferences

    internal var editor: SharedPreferences.Editor

    internal var PRIVATE_MODE = 0


    companion object {

        private val LOGIN_STATUS = "LOGIN_STATUS"
        private val PROFILE = "tafuta1_user_profile"
        private val FIREBASE_TOKEN = "firebasetoken"
        private val PREF_NAME = "tafuta1_prefrences"

    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    fun clearUser() {
        editor.clear()
        editor.commit()
    }

    fun setFirebase(token: String) {
        editor.putString(FIREBASE_TOKEN, token)
        editor.commit()
    }

    fun getFirebase(): String {
        return pref.getString(FIREBASE_TOKEN, "")!!
    }
    fun setLoginStatus(status: Int){
        editor.putInt(LOGIN_STATUS,status)
        editor.commit()
    }

    fun getLoginStatus(): Int {

        return pref.getInt(LOGIN_STATUS,0)
    }



}