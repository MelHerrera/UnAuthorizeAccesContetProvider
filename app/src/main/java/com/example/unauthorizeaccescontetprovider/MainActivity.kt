package com.example.unauthorizeaccescontetprovider

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var app1_result: TextView
    lateinit var app2_result: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        app1_result = findViewById(R.id.app1_result)
        app2_result = findViewById(R.id.app2_result)

        val app1UriString = "content://com.example.exposedcontent.provider/t1"
        val app2UriString = "content://com.example.protectedcontent.provider/t1"

        val cols = arrayOf("_ID","MESSAGE")

        var u = Uri.parse(app1UriString)
        var c = contentResolver.query(u,cols, null, null, null)

        if (c != null) {
            if(c.moveToFirst())
                app1_result.text = "Content Accesed: "+ c.getString(c.getColumnIndexOrThrow("MESSAGE"))
            else
                app1_result.text = "Access denied: Security Exception"
        }

        //reading from protected content
        u = Uri.parse(app2UriString)

        try{
            c = contentResolver.query(u, cols, null, null, null)

            if (c != null) {
                if(c.moveToFirst())
                    app2_result.text = "Content Accesed: " + c.getString(c.getColumnIndexOrThrow("MESSAGE"))
                else
                    app2_result.text = "Cannot access content "
            }
        }catch (e:SecurityException){
            app2_result.text = "Access denied: Security exception"
        }
    }
}