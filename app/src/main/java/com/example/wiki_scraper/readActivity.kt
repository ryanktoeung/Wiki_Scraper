package com.example.wiki_scraper

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.File
import java.io.IOException


class readActivity : AppCompatActivity() {

    private var endTxt = ""
    private var endTitle = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val search = findViewById<EditText>(R.id.searchTxt)
        val go = findViewById<Button>(R.id.btnParseHTML)
        val save = findViewById<Button>(R.id.saveBTN)
        val accessFile = findViewById<Button>(R.id.accessFilesBtn)
        val textView = findViewById<TextView>(R.id.outputTxt)

        supportActionBar?.title = "Search for Wikipedia"



        go.setOnClickListener {
            val url:String = getWikiURL(search)
            getHtmlFromWeb(url, textView)
        }
        save.setOnClickListener {
            Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT)
                .show();

            val path = this.getExternalFilesDir(null)

            val folder = File(path, "pages")
            folder.mkdirs()

            val file = File(folder, "$endTitle.txt")
            file.appendText("$endTxt")

            val titleFile = File(folder, "titles.txt")
            titleFile.appendText("$endTitle \n")
        }

        accessFile.setOnClickListener{
            val i = Intent(
                this,
                getActivity::class.java
            );
            startActivity(i);
        }
    }

    private fun getWikiURL(search:EditText) : String
    {
        var startText:String = "https://www.google.co.in/search?q=wikipedia+"
        var check:String = search.text.toString()
        var url = "";
        check = check.replace("\\s".toRegex(), "+");
        for(element in check)
        {
            startText += element;
        }

        val policy = StrictMode.ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val doc = Jsoup.connect(startText).get()
        val links = doc.select("a[href]")
        for(link in links)
        {
            if(link.attr("href").contains("en.wikipedia.org/wiki/"))
                return link.attr("href")
        }
        return ""
    }


    private fun getHtmlFromWeb(url: String, textView: TextView) {
        Thread(Runnable {
            val stringBuilder = StringBuilder()
            //var urlString:String = url.text.toString()
            try {
                val doc: Document = Jsoup.connect(url).get()

                val title: String = doc.title()
                endTitle = title
                val body: Elements = doc.select("p")

                stringBuilder.append(title).append("\n")
                for (body in body) {
                    stringBuilder.append("\n").append(body.text())
                }

            } catch (e: IOException) {
                stringBuilder.append("Error : ").append(e.message).append("\n")
            }

            endTxt = stringBuilder.toString();
            runOnUiThread { textView.text = stringBuilder.toString() }
        }).start()
    }
}
