package org.utarid.contentprovider

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.utarid.contentprovider.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE: Int = 1
    private var globalContactsList: MutableList<String> = ArrayList<String>()
    private var binding: ActivityMainBinding? = null
    private var mAdapter: ContactsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        binding!!.btnGetContacts.setOnClickListener {
            prepareRecyclerView()
            requestPerm()
        }
    }

    private fun prepareRecyclerView() {

        val recyclerView: RecyclerView = binding!!.recyclerView
        mAdapter = ContactsAdapter(globalContactsList)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter
    }

    private fun getContacts() {
        val resolver: ContentResolver = contentResolver
        val uri: Uri = ContactsContract.Contacts.CONTENT_URI
        val projectionData: String = ContactsContract.Contacts.DISPLAY_NAME
        val projection = arrayOf(projectionData)
        val cursor: Cursor? = resolver.query(uri, projection, null, null, projectionData)

        when (cursor?.count) {
            null -> {
                //error
            }

            0 -> {
                //no contact
            }

            else -> {
                while (cursor.moveToNext()) {
                    val data: String = cursor.getString(cursor.getColumnIndex(projectionData))
                    globalContactsList.add(data)
                }
                cursor.close()
                mAdapter?.notifyDataSetChanged()
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