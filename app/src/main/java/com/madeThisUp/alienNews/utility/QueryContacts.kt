package com.madeThisUp.alienNews.utility

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.ContactsContract

data class ContactInfo(
    val url: String?,
    val hasPhoneNumber: Boolean,
    val contactId: String?,
) {
    fun hasRequiredFieldsForPinQuery() = hasPhoneNumber && contactId != null
}

object QueryContacts {

    fun queryContactInfo(contentResolver: ContentResolver, uri: Uri): ContactInfo {
        var contactInfo: ContactInfo? = null
        val fields = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts._ID
        )
        val queryResult = contentResolver.query(
            uri, fields, null, null
        )
        queryResult?.use { cursor ->
            if (cursor.moveToFirst()) {
                contactInfo = ContactInfo(
                    url = cursor.getString(0),
                    hasPhoneNumber = cursor.getString(1) == "1",
                    contactId = cursor.getString(2)
                )
            }
            cursor.close()
        }
        return contactInfo ?: ContactInfo(null, false, null)
    }

    @SuppressLint("Range")
    fun queryPinCode(contentResolver: ContentResolver, contactId: String): String? {
        var number = ""
        val queryResult = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
        queryResult?.use { cursor ->
            if (cursor.moveToFirst()) {
                number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
            cursor.close()
        }
        number.ifBlank { return null }
        return number
    }

}