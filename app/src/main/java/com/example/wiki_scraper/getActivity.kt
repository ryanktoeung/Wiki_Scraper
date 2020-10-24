package com.example.wiki_scraper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class getActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)

        val output = findViewById<TextView>(R.id.outputTxt)

        val i: Intent = intent;
        val listOfVals = i.getStringArrayListExtra("listOfVals")

        var index:String = ""
        var title:String = ""
        var url:String = ""

        for(i in 0 until listOfVals.size)
        {
            index = listOfVals[i].substring(0, listOfVals[i].indexOf("."))
            title = listOfVals[i].substring(listOfVals[i].indexOf("."), listOfVals[i].indexOf("("))
            url = listOfVals[i].substring(listOfVals[i].indexOf("("), listOfVals[i].indexOf(")"))
            output.setText( listOfVals[i] )
        }
    }
}
