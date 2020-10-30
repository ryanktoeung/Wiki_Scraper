package com.example.wiki_scraper

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class getActivity : AppCompatActivity() {

    private var titleChosen = "";
    private var listOfTitles = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)

        val choices = findViewById<Button>(R.id.titleBtn)
        val outTxt = findViewById<TextView>(R.id.outTxt)
        val start = findViewById<Button>(R.id.getDataBtn)

        supportActionBar?.title = "Access Saved Results"
        setArrayOfTitles()

        choices.setOnClickListener {
            val titles = listOfTitles.toTypedArray()
            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Choose the title")
            builder.setItems(titles, DialogInterface.OnClickListener { dialog, which ->
                titleChosen = titles[which]
                if(titleChosen[titleChosen.length-1] == ' ')        //Remove the end space
                    titleChosen = titleChosen.substring(0, titleChosen.length - 1);
            })
            builder.show()
        }


        start.setOnClickListener {
            val path = this.getExternalFilesDir(null)

            val folder = File(path, "pages")
            folder.mkdirs()
            val inStream = File(folder,"$titleChosen.txt").inputStream()
            val inString = inStream.bufferedReader().use{it.readText()}
            if(inString != null)
                outTxt.text = inString;
            else
                outTxt.text = "Nothing available"
        }

    }
    private fun setArrayOfTitles() {
        var size = 0
        val path = this.getExternalFilesDir(null)
        val folder = File(path, "pages")
        folder.mkdirs()
        val titleFile = File(folder, "titles.txt").readLines()
        for (element in titleFile) {
            if(!element.isBlank())
                if(listOfTitles.isEmpty())
                    listOfTitles.add(element)
                else
                {
                    if(listOfTitles.size == 1)  //If size is 1, it will set to 1
                        size = 1
                    else
                        size = listOfTitles.size - 1;   //Other wise go to size - 1
                    for (i in 0 until size)
                    {
                        if (listOfTitles[i] != element)
                            if(!listOfTitles.contains(element))
                                listOfTitles.add(element)
                    }
                }
        }
    }

}
