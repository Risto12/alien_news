package com.madeThisUp.alienNews.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.DialogFragment
import com.madeThisUp.alienNews.R
import com.madeThisUp.alienNews.utility.PhonePermissionHandler.hasPermission

class ConnectFragment : DialogFragment() {

    private var txtApiUrl: EditText? = null
    private var txtPassword: EditText? = null
    @SuppressLint("Range") // TODO FIX
    private val contactsActivityResult = registerForActivityResult(ActivityResultContracts.PickContact()) { uri ->
        uri?.let {
            if(txtApiUrl != null) {
                var hasPhoneNumber = false
                var personId = ""
                val fields = arrayOf(
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
                    ContactsContract.Contacts._ID
                )
                val contentResolver = requireActivity().contentResolver
                val queryResult = contentResolver.query(
                    uri, fields, null, null
                )
                queryResult?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val contact = cursor.getString(0)
                        txtApiUrl?.setText(contact)
                        if(cursor.getString(1) == "1") {
                            hasPhoneNumber = true
                            personId = cursor.getString(2)
                        }
                    }
                }
                if(hasPhoneNumber && txtPassword != null) {
                    val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ personId,null, null);
                    phones!!.moveToFirst();
                    val number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    txtPassword?.setText(number)
                }
            }
        }
    }

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if(granted) {

        }
    }

    private fun systemHasFeature(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? = packageManager.resolveActivity(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY, // restrict search to default category
        )
        return resolvedActivity != null
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

            txtApiUrl = inflater.findViewById(R.id.alienUrl)
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
                if(hasPermission(requireContext())) {

                } else {
                    permissionResult.launch(Manifest.permission.READ_CONTACTS)
                }
            }
            getButton(Dialog.BUTTON_NEUTRAL).setOnClickListener {
                val contactListFeat =
                    contactsActivityResult.contract.createIntent(requireContext(), null)
                if(systemHasFeature(contactListFeat)) {
                    contactsActivityResult.launch(null)
                } else {
                    Toast.makeText(requireContext(), "Not available functionality", Toast.LENGTH_LONG).show()
                }
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
