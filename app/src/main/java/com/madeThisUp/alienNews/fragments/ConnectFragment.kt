package com.madeThisUp.alienNews.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.madeThisUp.alienNews.R

class ConnectFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity()
                .layoutInflater
                .inflate(R.layout.connection_settings_dialog, null)
            builder.setView(inflater)
                .setPositiveButton(R.string.connect
                ) { _, _ ->
                    val url = inflater.findViewById<EditText>(R.id.username)
                    val password = inflater.findViewById<EditText>(R.id.password)
                    // TODO test connection
                }
                .setNegativeButton(R.string.cancel
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                .setNeutralButton(R.string.contacts) {
                    _, _ -> Toast.makeText(requireContext(), "contacs button", Toast.LENGTH_LONG).show()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()
    }
}
