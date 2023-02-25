package com.madeThisUp.alienNews.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.madeThisUp.alienNews.R
import com.madeThisUp.alienNews.newsApi.NETWORK_ERROR_TAG
import com.madeThisUp.alienNews.repository.*
import com.madeThisUp.alienNews.utility.QueryContacts
import com.madeThisUp.alienNews.utility.showLongToastText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Purpose of this fragment is to show dialog that prompts user for username and password
 * to acquire connection to news api.
 * User can also fetch the username and password from phones contacts. Contacts name is used as username
 * and phone number as password.
 * If the connection is successful they are stored to shared preference as is the token
 */
class ConnectFragment : DialogFragment() {

    private var txtUsername: EditText? = null
    private var txtPassword: EditText? = null
    private var newsRepository = TokenNewsRepositoryImpl()

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

    private fun credentialFieldsAreValid(): Boolean {
        if(txtUsername == null || txtPassword == null) return false
        if(txtUsername!!.text.toString().isEmpty()) {
            requireContext().showLongToastText("Username can't be empty")
            return false
        }
        if(txtPassword!!.text.toString().isEmpty()) {
            requireContext().showLongToastText( "Password can't be empty")
            return false
        }
        return true
    }

    private fun fetchToken() {
        lifecycleScope.launch {
            if(credentialFieldsAreValid()) {
                try {
                    val username = txtUsername!!.text.toString()
                    val password = txtPassword!!.text.toString()
                    newsRepository.acquireToken(username, password)
                    CredentialsPreferencesRepository
                        .get()
                        .saveCredentials(username, password)
                    dismiss()
                } catch(e: NewsRepositoryAuthenticationException) {
                    requireContext().showLongToastText("Authentication not valid")
                    Log.e(NETWORK_ERROR_TAG, "Authentication was not valid", e)
                } catch(e: NewsRepositoryException) {
                    requireContext().showLongToastText("Unknown issue during login attempt")
                    Log.e(NETWORK_ERROR_TAG, "Exception during fetching token", e)
                }
            }
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

    private fun setUpCredentialsViews(view: View) {
        txtUsername = view.findViewById(R.id.username)
        txtPassword = view.findViewById(R.id.password)
        lifecycleScope.launch {
            CredentialsPreferencesRepository.get().credentialsFlow.collectLatest {
               txtUsername?.setText(it.username ?: "")
               txtPassword?.setText(it.password ?: "")
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity()
                .layoutInflater
                .inflate(R.layout.connection_settings_dialog, null)

            setUpCredentialsViews(inflater)

            builder.setView(inflater)
                .setPositiveButton(R.string.connect, null)
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .setNeutralButton(R.string.contacts, null)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()
        val a = dialog
        val alertDialog = dialog as AlertDialog
        alertDialog.apply {
            getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
                fetchToken()
            }
            getButton(Dialog.BUTTON_NEUTRAL).setOnClickListener {
                val contactListFeat =
                    contactsActivityResult.contract.createIntent(requireContext(), null)
                if(systemHasFeature(contactListFeat)) {
                    contactsActivityResult.launch(null)
                } else {
                    requireContext().showLongToastText("Not available functionality")
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        txtPassword = null
        txtUsername = null
    }
}
