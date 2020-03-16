package co.wangun.uvs.utils

import android.content.Context

class SessionManager(context: Context) {

    private val pref = context.getSharedPreferences("Preferences", 0)
    private val editor = pref.edit()

    // Put Session
    fun clearSession() {
        editor.clear()
        editor.apply()
    }

    fun putFace(
        face: String
    ) {
        editor.putString("Face", face)
        editor.apply()
    }

    fun putName(
        name: String
    ) {
        editor.putString("Name", name)
        editor.apply()
    }

    fun putTarget(
        target: String
    ) {
        editor.putString("Target", target)
        editor.apply()
    }

    fun putPhone(
        phone: String
    ) {
        editor.putString("Phone", phone)
        editor.apply()
    }

    fun putDate(
        date: String
    ) {
        editor.putString("Date", date)
        editor.apply()
    }

    fun putTime(
        time: String
    ) {
        editor.putString("Time", time)
        editor.apply()
    }

    fun putPath(
        path: String
    ) {
        editor.putString("Path", path)
        editor.apply()
    }

    fun putStatus(
        status: Int
    ) {
        editor.putInt("Status", status)
        editor.apply()
    }

    fun putLoc(
        lat: String,
        lng: String
    ) {
        editor.putString("Lat", lat)
        editor.putString("Lng", lng)
        editor.apply()
    }

    // Get Session
    fun getPath(): String? {
        return pref.getString("Path", "No Path")
    }

    fun getTarget(): String? {
        return pref.getString("Target", "No Target")
    }

    fun getPhone(): String? {
        return pref.getString("Phone", "No Phone")
    }

    fun getDate(): String? {
        return pref.getString("Date", "No Date")
    }

    fun getTime(): String? {
        return pref.getString("Time", "No Time")
    }

    fun getName(): String? {
        return pref.getString("Name", "No Name")
    }

    fun getFace(): String? {
        return pref.getString("Face", "No Face")
    }

    fun getStatus(): Int? {
        return pref.getInt("Status", 0)
    }

    fun getLoc(loc: String): String? {
        val lat = pref.getString("Lat", "No Lat")
        val lng = pref.getString("Lng", "No Lng")

        return when (loc) {
            "lat" -> lat
            "lng" -> lng
            else -> "LAT $lat, LNG $lng"
        }
    }
}


