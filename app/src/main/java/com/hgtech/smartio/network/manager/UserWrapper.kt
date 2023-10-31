package com.hgtech.smartio.network.manager

import com.pixplicity.easyprefs.library.Prefs
import org.koin.core.component.KoinComponent
import java.text.SimpleDateFormat
import java.util.*


object UserWrapper : KoinComponent {

    fun String.checkActive(): Boolean {
        return this == "1"
    }

    fun getDate(): String {
        val c: Date = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return df.format(c)
    }

    fun Boolean.get(): String {
        return if (this)
            "1"
        else
            "0"
    }

    val USER_ID = "USER_ID"

    fun setID(id: String?, force: Boolean = false) {
        if (id != null || force)
            Prefs.putString(USER_ID, id)
    }

    fun getID(): String {
        return Prefs.getString(USER_ID, "0") ?: "0"
    }
}

