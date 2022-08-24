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
    var message = " Nominal : Rp.30.000 \n Provider : IM3 \n No Pengirim : (--no pengirim) \n NoBank: 1234567 \n nama Bank : BANK BCA"
    var phoneId = "+628815089298".replace("+", "").replace(" ", "")
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
        var button4 = findViewById<Button>(R.id.btn4)
        button4.setOnClickListener {
            openWhatsappWeb()
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


        try {
            val intent = Intent("android.intent.action.MAIN")
            intent.putExtra("jid", "$phoneId@s.whatsapp.net")
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.action = Intent.ACTION_SEND
            intent.setPackage("com.whatsapp")
            intent.setType("text/plain")
            startActivity(intent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this, "Please install WhatsApp App", Toast.LENGTH_SHORT).show()
        } catch (exception: NullPointerException) {
            openWhatsappWeb()
        }
    }

    fun launchWhatsAppBusinessApp() {

        try {

            val intent = Intent("android.intent.action.MAIN")
            intent.putExtra("jid", "$phoneId@s.whatsapp.net")
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.action = Intent.ACTION_SEND
            intent.setPackage("com.whatsapp")
            intent.setType("text/plain")
            startActivity(intent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this, "Please install WA Business App", Toast.LENGTH_SHORT).show()
        } catch (exception: java.lang.NullPointerException) {
            openWhatsappWeb()
        }
    }

    fun openWhatsappWeb() {
//        val message: String = "mMessOpenWhatEdit.getText().toString() "// take message from the user

        // create an Intent to send data to the whatsapp
        val intent = Intent(Intent.ACTION_VIEW) // setting action

        // setting data url, if we not catch the exception then it shows an error
        try {
            val url =
                "https://api.whatsapp.com/send?phone=+628815089298" + "&text=" + URLEncoder.encode(
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