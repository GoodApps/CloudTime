package com.cloudtime.ui.login

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.cloudtime.R
import com.parse.ParseUser
import kotlin.properties.Delegates

/**
 * Created by KD on 2015-06-16.
 */
public class LoginDialog : android.support.v4.app.DialogFragment() {

    public interface DialogListener {
        public fun onDialogPositiveClick()
        public fun onDialogNegativeClick()
    }

    var mListener: DialogListener by Delegates.notNull()

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        try {
            mListener = activity as DialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(activity!!.toString() + " must implement NoticeDialogListener")
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val fragmentActivity = getActivity()
        val builder = AlertDialog.Builder(fragmentActivity)
        val inflater = fragmentActivity.getLayoutInflater()

        builder.setView(inflater.inflate(R.layout.login_dialog, null))

        builder.setMessage("Log in")
                .setPositiveButton("Log in", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, id: Int) {
                val dialog: Dialog? = getDialog()
                val usernameEdit = dialog!!.findViewById(R.id.username_edit) as EditText
                val username = "" + usernameEdit.getText()
                val password = "" + (dialog!!.findViewById(R.id.password_edit) as EditText).getText()
                ParseUser.logInInBackground( username, password, { user, exc ->
                    val message = "Log in: $user --- $exc"
                    Toast.makeText(fragmentActivity, message, Toast.LENGTH_LONG).show();
                    Log.d("parse_login", message)
                })
                //mListener.onDialogPositiveClick()
            }
        }).setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, id: Int) {
                dismiss()
//                mListener.onDialogNegativeClick()
            }
        })
        return builder.create()
    }


}