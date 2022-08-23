package com.didik.whatapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button = findViewById<Button>(R.id.btn)
        button.setOnClickListener {
            openWhatsApp()
        }
        var button2 = findViewById<Button>(R.id.btn2)
        button2.setOnClickListener {
            launchWhatsAppBusinessApp()
        }
        var button3 = findViewById<Button>(R.id.btn3)
        button3.setOnClickListener {

        when {
            isAppInstalled("com.whatsapp") -> {
                openWhatsApp()
            }
            isAppInstalled("com.whatsapp.w4b") -> {
                launchWhatsAppBusinessApp()
            }
            else -> {
                openWhatsappWeb()
            }
        }
        }

    }


    private fun isAppInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (ignored: PackageManager.NameNotFoundException) {
            false
        }
    }
    fun openWhatsApp() {
        val pm = packageManager
        try {
            val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
            val intent = this.packageManager.getLaunchIntentForPackage("com.whatsapp")
            startActivity(intent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this, "Please install WhatsApp App", Toast.LENGTH_SHORT).show()
        } catch (exception: NullPointerException) {
        }
    }

    fun launchWhatsAppBusinessApp() {
        val pm = packageManager
        try {
            val info = pm.getPackageInfo("com.whatsapp.w4b", PackageManager.GET_META_DATA)
            val intent = this.packageManager.getLaunchIntentForPackage("com.whatsapp.w4b")
            startActivity(intent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this, "Please install WA Business App", Toast.LENGTH_SHORT).show()
        } catch (exception: java.lang.NullPointerException) {
        }
    }

    fun openWhatsappWeb() {
        val message: String = "mMessOpenWhatEdit.getText().toString() "// take message from the user

        // create an Intent to send data to the whatsapp
        val intent = Intent(Intent.ACTION_VIEW) // setting action

        // setting data url, if we not catch the exception then it shows an error
        try {
            val url =
                "https://api.whatsapp.com/send?phone=+6285706916051" + "&text=" + URLEncoder.encode(
                    message,
                    "UTF-8"
                )
            intent.data = Uri.parse(url)

            startActivity(intent)
        } catch (e: UnsupportedEncodingException) {
            Log.d("notSupport", "thrown by encoder")
        }
    }
}