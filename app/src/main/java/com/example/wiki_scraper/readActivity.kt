package com.example.wiki_scraper

import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_read.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException


class readActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val search = findViewById<EditText>(R.id.searchTxt)
        val go = findViewById<Button>(R.id.btnParseHTML)
        val textView = findViewById<TextView>(R.id.outputTxt)


        go.setOnClickListener {
            val url:String = getWikiURL(search)
            println("URL : " + url);
            getHtmlFromWeb(url,textView);
        }
    }

    private fun getWikiURL(search:EditText) : String
    {
        var startText:String = "https://www.google.co.in/search?q=wikipedia+"
        var check:String = search.text.toString()
        check = check.replace("\\s".toRegex(), "_");
        for(element in check)
        {
            startText += element;
        }
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val doc:Document = Jsoup.connect(startText).get()
        return doc.select("h3 a").attr("href")
    }


    private fun getHtmlFromWeb(url: String, textView: TextView) {
        Thread(Runnable {
            val stringBuilder = StringBuilder()
            //var urlString:String = url.text.toString()
            try {
                val doc: Document = Jsoup.connect(url).get()

                val title: String = doc.title()
                val body: Elements = doc.select("p")

                stringBuilder.append(title).append("\n")
                for (body in body) {
                    stringBuilder.append("\n").append(body.text())
                }
            } catch (e: IOException) {
                stringBuilder.append("Error : ").append(e.message).append("\n")
            }
            runOnUiThread { outputTxt.text = stringBuilder.toString() }
        }).start()
    }
    /*
fun scrapeWiki(wiki: EditText)
{

    var title:String = ""
    var url:String = ""
    var indexVal = 0


    val listOfWord:List<String> = emptyList()

    println(startText);

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
     */
}
