package com.example.wiki_scraper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val read = findViewById<Button>(R.id.readBtn)
        val get = findViewById<Button>(R.id.getBtn)

        read.setOnClickListener {
            val i = Intent(
                this,
                readActivity::class.java
            );
            startActivity(i);
        }

        get.setOnClickListener {
            val i = Intent(
                this,
                getActivity::class.java
            );
            startActivity(i);
        }
    }
}
