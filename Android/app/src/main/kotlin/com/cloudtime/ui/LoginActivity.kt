package com.cloudtime.ui

import android.os.Bundle
import com.cloudtime.R
import com.cloudtime.ui.common.BaseActivity

public class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
    }
}
