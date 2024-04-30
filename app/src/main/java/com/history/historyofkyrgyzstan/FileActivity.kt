package com.history.historyofkyrgyzstan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.history.historyofkyrgyzstan.databinding.ActivityFileBinding

class FileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            openPdfFromInternet()
        }
    }

    private fun openPdfFromInternet() {
        val pdfUrl =
            "https://github.com/pdf-association/pdf20examples/blob/master/PDF%202.0%20image%20with%20BPC.pdf"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        // Check if there is an activity available to handle the intent
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            // Handle the case where no activity is available
            Toast.makeText(this, "No application available to view PDF", Toast.LENGTH_SHORT).show()
        }
    }
}