package com.srpc.independantminds.model.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


/**
 * Created by Miriana on 1/30/2018.
 */
open class Prefs {

    companion object {

        private var INSTANCE: Prefs? = null
        private var prefs: SharedPreferences? = null

        fun getInstance(context: Context): Prefs {
            if (INSTANCE == null) {
                INSTANCE = Prefs()
                prefs = PreferenceManager.getDefaultSharedPreferences(context)
            }

            return INSTANCE!!
        }
    }

    fun clear() {
        prefs!!.edit().clear().apply()
    }

    fun setPage(isLogin: Int) {
        prefs!!.edit().putInt("page", isLogin).apply()
    }

    fun getPage(): Int {
        return prefs!!.getInt("page", -1)
    }

    fun setNotification(isLogin: Boolean) {
        prefs!!.edit().putBoolean("notification", isLogin).apply()
    }

    fun getNotification(): Boolean {
        return prefs!!.getBoolean("notification", true)
    }

    fun setAccept(isLogin: Boolean) {
        prefs!!.edit().putBoolean("accept", isLogin).apply()
    }

    fun getAccept(): Boolean {
        return prefs!!.getBoolean("accept", true)
    }


    fun setLoad(isLogin: Boolean) {
        prefs!!.edit().putBoolean("load", isLogin).apply()
    }

    fun getLoad(): Boolean {
        return prefs!!.getBoolean("load", true)
    }

    fun setFirstTime(isLogin: Boolean) {
        prefs!!.edit().putBoolean("firstTime", isLogin).apply()
    }

    fun getFirstTime(): Boolean {
        return prefs!!.getBoolean("firstTime", true)
    }

    fun setLat(isLogin: String) {
        prefs!!.edit().putString("lat", isLogin).apply()
    }

    fun getLat(): String? {
        return prefs!!.getString("lat", "")
    }

    fun setLng(isLogin: String) {
        prefs!!.edit().putString("lng", isLogin).apply()
    }

    fun getLng(): String? {
        return prefs!!.getString("lng", "")
    }
}