package com.madeThisUp.alienNews.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.madeThisUp.alienNews.R

class ConnectFragment : DialogFragment() {

    private var txtApiUrl: EditText? = null
    private var txtPassword: EditText? = null
    private val contactsActivityResult = registerForActivityResult(ActivityResultContracts.PickContact()) { uri ->
        uri?.let {
            if(txtApiUrl != null) {
                val fields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
                val queryResult = requireActivity().contentResolver.query(
                    uri, fields, null, null
                )
                queryResult?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val contact = cursor.getString(0)
                        txtApiUrl?.setText(contact)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity()
                .layoutInflater
                .inflate(R.layout.connection_settings_dialog, null)

            txtApiUrl = inflater.findViewById(R.id.url)
            txtPassword = inflater.findViewById(R.id.password)

            builder.setView(inflater)
                .setPositiveButton(R.string.connect, null)
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .setNeutralButton(R.string.contacts, null)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()
        val alertDialog = dialog as AlertDialog
        alertDialog.apply {
            getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
                // TODO verify fields
                Toast.makeText(requireContext(), txtApiUrl!!.text.toString(), Toast.LENGTH_LONG).show()
            }
            getButton(Dialog.BUTTON_NEUTRAL).setOnClickListener {
                contactsActivityResult.launch(null)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // TODO check if this is even necessary
        txtPassword = null
        txtApiUrl = null
    }
}
