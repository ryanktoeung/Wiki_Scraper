package com.example.wiki_scraper

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup


class readActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val wiki = findViewById<EditText>(R.id.wiki_URL_txt)
        val go = findViewById<Button>(R.id.goBtn)


        go.setOnClickListener {
            scrapeWiki(wiki);
        }
    }

    fun scrapeWiki(wiki: EditText)
    {
        var title:String = ""
        var url:String = ""
        var indexVal = 0
        var startText:String = "https://www.google.co.in/search?q="
        var check:String = wiki.text.toString()
        check = check.replace("\\s".toRegex(), "+");
        val listOfWord:List<String> = emptyList()
        for(element in check)
        {
            startText += element;
        }
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        Jsoup.connect(startText).get().run{
                select("div.rc").forEachIndexed{index, element ->
                val titleAnchor = element.select("h3 a")        //Title is not going through
                title = titleAnchor.text()
                url = titleAnchor.attr("href")                          //URL is not getting read
                indexVal = index
                val i:Intent = Intent(this@readActivity, getActivity::class.java)
                var listOfVals = arrayListOf<String>()
                listOfVals.add("$indexVal. $title ($url)")
                i.putStringArrayListExtra("listOfVals", listOfVals)
                startActivity(i)
        }

//            println("$index. $title ($url)")
        }
    }

}
