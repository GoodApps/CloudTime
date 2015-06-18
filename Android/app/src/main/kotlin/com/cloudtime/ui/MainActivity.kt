package com.cloudtime.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.ContextMenu
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cloudtime.R
import com.cloudtime.dto.Timer
import com.cloudtime.service.TimerService
import com.cloudtime.ui.common.BaseActivity
import com.cloudtime.ui.login.LoginDialog
import com.parse.ParseUser
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

public class MainActivity : BaseActivity(), LoginDialog.DialogListener {

    private var subscription: Subscription by Delegates.notNull()

    private var listView: RecyclerView by Delegates.notNull()
    private var loginButton: Button by Delegates.notNull()

    private val adapter: TimersAdapter = TimersAdapter(activity = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.main_toolbar) as Toolbar)
        initEditText()
        initListView()
        initLoginButton()

        registerReceiver()
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
//        intentFilter.addAction("com.google.android.c2dm.intent.RECEIVE")
        intentFilter.addAction("com.parse.push.intent.RECEIVE")
//        intentFilter.addAction("com.parse.push.intent.DELETE")
//        intentFilter.addAction("com.parse.push.intent.OPEN")
//        intentFilter.addAction("com.example.UPDATE_STATUS")

        registerReceiver(PushNotificationBroadcastReceiver(), intentFilter)
    }

    inner class PushNotificationBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "MyBroadcastReceiver 2: onReceive: "
                    + context + ";" + intent, Toast.LENGTH_SHORT).show()

            loadTimers()
        }
    }

    /** Quick hackish version using ints, sorry */
    private val MENU_ITEM_COMMAND_DELETE_TIMER = 0
    private val MENU_ITEM_COMMAND_START_TIMER = 1
    private val MENU_ITEM_COMMAND_STOP_TIMER = 2


    private var currentContextMenuItem: Timer? = null

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super<BaseActivity>.onCreateContextMenu(menu, v, menuInfo)
        currentContextMenuItem = v.getTag() as Timer
        menu.setHeaderTitle("${currentContextMenuItem?.title}")
        //
        menu.add(0, MENU_ITEM_COMMAND_DELETE_TIMER, 0, "Delete")
        menu.add(0, MENU_ITEM_COMMAND_START_TIMER, 0, "Start")
        menu.add(0, MENU_ITEM_COMMAND_STOP_TIMER, 0, "Stop")
//        menu.add(0, v.getId(), 0, "Action 3")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {

            MENU_ITEM_COMMAND_DELETE_TIMER -> TimerService().deleteEventually(currentContextMenuItem)

            MENU_ITEM_COMMAND_START_TIMER -> currentContextMenuItem?.startTimer()

            MENU_ITEM_COMMAND_STOP_TIMER -> currentContextMenuItem?.stopTimer()
        }
        return true
    }


    private fun initEditText() {
        val editText = findViewById(R.id.main_edit) as EditText
        editText.setOnEditorActionListener { v, actionId, event ->
            if (event == null || event.getAction() == KeyEvent.ACTION_UP) {
                val text = editText.getText().toString().trim()
                if (text.isNotEmpty()) {
                    addTimer(15, TimeUnit.MINUTES, text)
                    editText.setText("")
                }
            }
            true
        }
    }

    private fun initListView() {
        listView = findViewById(R.id.main_list) as RecyclerView
        listView.setLayoutManager(LinearLayoutManager(this))
        listView.setAdapter(adapter)
    }

    private fun initLoginButton() {
        loginButton = findViewById(R.id.login_button) as Button
        loginButton.setOnClickListener {
            val dialog = LoginDialog();
            dialog.show(getSupportFragmentManager(), "Log In");
        }
        if ( ParseUser.getCurrentUser() != null ) {
            loginButton.setText("Change user")
        }
    }


    override fun onResume() {
        super<BaseActivity>.onResume()
        loadTimers()
    }

    override fun onPause() {
        super<BaseActivity>.onPause()
        cancelLoading()
    }

    private fun addTimer(duration: Long, unit: TimeUnit, title: String) {
        TimerService().addTimer(duration, unit, title)
    }

    private fun loadTimers() {
        subscription = TimerService().loadTimers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ updateAdapter(it) }, { handleError(it) })
    }

    private fun updateAdapter(timers: List<Timer>) {
        adapter.update(timers)
    }

    private fun handleError(t: Throwable) {
        Log.e("tag", "error", t)
    }

    private fun cancelLoading() {
        subscription.unsubscribe()
    }

    override fun onDialogPositiveClick() {
        throw UnsupportedOperationException()
    }

    override fun onDialogNegativeClick() {
        throw UnsupportedOperationException()
    }


}
