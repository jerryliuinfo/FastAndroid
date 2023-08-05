package com.apache.fastandroid.demo.storage.opendocument

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.fragment.app.transaction
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentOpenPdfBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/8/5.
 */

class OpenDocumentDemoFragment:BaseBindingFragment<FragmentOpenPdfBinding>(FragmentOpenPdfBinding::inflate) {

    companion object{
        const val DOCUMENT_FRAGMENT_TAG = "com.example.android.actionopendocument.tags.DOCUMENT_FRAGMENT"
        private const val OPEN_DOCUMENT_REQUEST_CODE = 0x33
        private const val TAG = "MainActivity"
        private const val LAST_OPENED_URI_KEY =
            "com.example.android.actionopendocument.pref.LAST_OPENED_URI_KEY"
    }

    private lateinit var noDocumentView: ViewGroup


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        noDocumentView = findViewById(R.id.no_document_view)
        findViewById<Button>(R.id.open_file).setOnClickListener {
            openDocumentPicker()
        }

        /*requireContext().getSharedPreferences(TAG, Context.MODE_PRIVATE).let { sharedPreferences ->
            if (sharedPreferences.contains(LAST_OPENED_URI_KEY)) {
                val documentUri =
                    sharedPreferences.getString(LAST_OPENED_URI_KEY, null)?.toUri() ?: return@let
                openDocument(documentUri)
            }
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_open_pdf, menu)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                AlertDialog.Builder(requireContext())
                    .setMessage("This sample demonstrates how to use PdfRenderer to display PDF documents on the screen.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                return true
            }
            R.id.action_open -> {
                openDocumentPicker()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == OPEN_DOCUMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { documentUri ->

                /**
                 * Upon getting a document uri returned, we can use
                 * [ContentResolver.takePersistableUriPermission] in order to persist the
                 * permission across restarts.
                 *
                 * This may not be necessary for your app. If the permission is not
                 * persisted, access to the uri is granted until the receiving Activity is
                 * finished. You can extend the lifetime of the permission grant by passing
                 * it along to another Android component. This is done by including the uri
                 * in the data field or the ClipData object of the Intent used to launch that
                 * component. Additionally, you need to add FLAG_GRANT_READ_URI_PERMISSION
                 * and/or FLAG_GRANT_WRITE_URI_PERMISSION to the Intent.
                 *
                 * This app takes the persistable URI permission grant to demonstrate how, and
                 * to allow us to reopen the last opened document when the app starts.
                 */
                requireContext().contentResolver.takePersistableUriPermission(
                    documentUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                openDocument(documentUri)
            }
        }
    }

    private fun openDocumentPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            /**
             * It's possible to limit the types of files by mime-type. Since this
             * app displays pages from a PDF file, we'll specify `application/pdf`
             * in `type`.
             * See [Intent.setType] for more details.
             */
            type = "application/pdf"

            /**
             * Because we'll want to use [ContentResolver.openFileDescriptor] to read
             * the data of whatever file is picked, we set [Intent.CATEGORY_OPENABLE]
             * to ensure this will succeed.
             */
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, OPEN_DOCUMENT_REQUEST_CODE)
    }

    private fun openDocument(documentUri: Uri) {
        /**
         * Save the document to [SharedPreferences]. We're able to do this, and use the
         * uri saved indefinitely, because we called [ContentResolver.takePersistableUriPermission]
         * up in [onActivityResult].
         */
        requireContext().getSharedPreferences(TAG, Context.MODE_PRIVATE).edit {
            putString(LAST_OPENED_URI_KEY, documentUri.toString())
        }

        val fragment = ActionOpenDocumentFragment.newInstance(documentUri)
        requireActivity().supportFragmentManager.transaction {
            add(R.id.container, fragment, DOCUMENT_FRAGMENT_TAG)
        }

        // Document is open, so get rid of the call to action view.
        noDocumentView.visibility = View.GONE
    }
}