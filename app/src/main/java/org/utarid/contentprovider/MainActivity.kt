package org.utarid.contentprovider

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPerm()
    }

    private fun getContacts() {
        val resolver: ContentResolver = contentResolver
        val uri: Uri = ContactsContract.Contacts.CONTENT_URI
        val projectionData: String = ContactsContract.Contacts.DISPLAY_NAME
        val projection = arrayOf(projectionData)
        val cursor: Cursor? = resolver.query(uri, projection, null, null, projectionData)

        when (cursor?.count) {
            null -> {
            }

            0 -> {
            }

            else -> {
                while (cursor.moveToNext()) {
                    var data: String = cursor.getString(cursor.getColumnIndex(projectionData))
                }

                cursor.close();
            }
        }
    }

    private fun requestPerm() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // user has already given permission
            getContacts()
        } else {
            // ask for the permission.
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow in your app.
                    getContacts()
                } else {
                    // user denied permission
                }
                return
            }
        }
    }
}