package com.cloudtime

import android.app.Application
import com.parse.Parse
import com.parse.ParseACL
import com.parse.ParseInstallation
import com.parse.ParseUser

public class CloudTimeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initParse()
    }

    private fun initParse() {
        Parse.initialize(this, "pL0PLrNyjePVlFJa1p1siq84Pk0qJiy8EuzLbBxF", "UTvNS997O35LIH4GqH2uIFvwer9Sx5KZWM0ZxTDQ")
        ParseUser.enableAutomaticUser()
        setDefaultACL()
        initInstallation()
    }

    private fun setDefaultACL() {
        val withWriteAccessForCurrentUser = true
        val noPermissionsACL = ParseACL()
        ParseACL.setDefaultACL(noPermissionsACL, withWriteAccessForCurrentUser)
    }

    private fun initInstallation() {
        val installation = ParseInstallation.getCurrentInstallation()
        installation.put("user", ParseUser.getCurrentUser())
        installation.saveInBackground()
    }
}
