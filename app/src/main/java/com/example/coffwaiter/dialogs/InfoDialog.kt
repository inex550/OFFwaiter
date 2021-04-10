package com.example.coffwaiter.dialogs

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class InfoDialog(
    private val title: String = "Внимание",
    private val message: String,
    private val okListener: () -> Unit
): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ок") { _, _ -> okListener() }
            .create()
    }
}