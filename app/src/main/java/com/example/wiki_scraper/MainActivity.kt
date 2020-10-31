package com.example.wiki_scraper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val read = findViewById<Button>(R.id.readBtn)
        val get = findViewById<Button>(R.id.getBtn)

        supportActionBar?.title = "Home"

        // Check if titles.txt exists
        val fileName = "titles.txt"
        val path = this.getExternalFilesDir(null)
        val folder = File(path, "pages")
        val file = File(folder, fileName)

        val fileExists = file.exists()

        if(!fileExists){
            val folder = File(path, "pages")
            folder.mkdirs()

            val file = File(folder, fileName)
            file.appendText("")
        }

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
