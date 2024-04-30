package com.history.historyofkyrgyzstan

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth

class MyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create and return the AlertDialog instance
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.exit)
            .setMessage(R.string.exit_dialog)
            .setPositiveButton("OK") { dialog, which ->
                FirebaseAuth.getInstance().signOut()
                val intent= Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton(R.string.cansel) { dialog, which ->
                // Negative button clicked
            }
            .create()
    }
}
