package com.madeThisUp.alienNews.fragments

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.madeThisUp.alienNews.R
import com.madeThisUp.alienNews.newsApi.NewsRepositoryImpl
import com.madeThisUp.alienNews.utility.PhonePermissionHandler.hasPermission
import com.madeThisUp.alienNews.utility.QueryContacts
import kotlinx.coroutines.launch

class ConnectFragment : DialogFragment() {

    private var txtUsername: EditText? = null
    private var txtPassword: EditText? = null

    private val contactsActivityResult = registerForActivityResult(ActivityResultContracts.PickContact()) { uri ->
        uri?.let {
            if(txtUsername != null) {
                val contentResolver = requireActivity().contentResolver
                val contactInfo = QueryContacts.queryContactInfo(contentResolver, uri)
                contactInfo.run { if(username != null) txtUsername?.setText(username) }
                if(contactInfo.hasRequiredFieldsForPinQuery() && txtPassword != null) {
                    val pin = QueryContacts.queryPinCode(contentResolver, contactInfo.contactId!!)
                    pin?.let { txtPassword?.setText(it) }
                }
            }
        }
    }

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if(granted) {
            // TODO
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
        lifecycleScope.launch {
            val b = NewsRepositoryImpl()
            b.fetchChannelNews("endor")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity()
                .layoutInflater
                .inflate(R.layout.connection_settings_dialog, null)

            txtUsername = inflater.findViewById(R.id.username)
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
        txtUsername = null
    }
}
