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

        val search = findViewById<EditText>(R.id.searchTxt)     //Search bar
        val go = findViewById<Button>(R.id.btnParseHTML)         //Go button to search
        val save = findViewById<Button>(R.id.saveBTN)            //Save button to save to a text file
        val accessFile = findViewById<Button>(R.id.accessFilesBtn)  //Button to go to the "get" activity to access files
        val textView = findViewById<TextView>(R.id.outputTxt)     //Text box to output results

        supportActionBar?.title = "Search for Wikipedia"    //Set title for the page

        go.setOnClickListener {
            val url:String = getWikiURL(search)     //Get the wikipedia link from the google search
            getHtmlFromWeb(url, textView)           //Scrape the wikipedia
        }
        save.setOnClickListener {
            Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT)
                .show();

            val path = this.getExternalFilesDir(null)

            val folder = File(path, "pages")
            folder.mkdirs()

            val file = File(folder, "$endTitle.txt")        //Save text into the specific text file
            file.appendText("$endTxt")

            val titleFile = File(folder, "titles.txt")      //Saves titles into the title.txt file
            titleFile.appendText("$endTitle \n")
        }

        accessFile.setOnClickListener{
            val i = Intent(
                this,
                getActivity::class.java                             //Go to "get" activity to access files
            );
            startActivity(i);
        }
    }   //end onCreate

    private fun getWikiURL(search:EditText) : String    //Search google for key words and get the first wikipedia link
    {
        var startText:String = "https://www.google.co.in/search?q=wikipedia+"       //Google search "wikipedia + [keyword]"
        var check:String = search.text.toString()
        var url = "";
        check = check.replace("\\s".toRegex(), "+");                    //Replace spaces with + to work with a google search
        for(element in check)
        {
            startText += element;       //Add text into google search
        }

        val policy = StrictMode.ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val doc = Jsoup.connect(startText).get()
        val links = doc.select("a[href]")
        for(link in links)
        {
            if(link.attr("href").contains("en.wikipedia.org/wiki/"))        //If the link has the wikipedia link, take it
                return link.attr("href")
        }
        return ""
    }// end getWikiURL

    private fun getHtmlFromWeb(url: String, textView: TextView) {                   //Scrape the wikipedia
        Thread(Runnable {
            val stringBuilder = StringBuilder()
            try {
                val doc: Document = Jsoup.connect(url).get()

                val title: String = doc.title()                                     //Get title of the page
                endTitle = title
                val body: Elements = doc.select("p")                        //Get body of the page

                stringBuilder.append(title).append("\n")
                for (body in body) {
                    stringBuilder.append("\n").append(body.text())                   //Add text
                }

            } catch (e: IOException) {
                stringBuilder.append("Error : ").append(e.message).append("\n")
            }

            endTxt = stringBuilder.toString();
            runOnUiThread { textView.text = stringBuilder.toString() }                  //Output final text
        }).start()
    }   //end getHtmlFromWeb
}   //end readActivity
