package com.example.wiki_scraper

import android.net.wifi.aware.ParcelablePeerHandle
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class getActivity : AppCompatActivity() {

    private var titleChosen = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)

        val choices = findViewById<Spinner>(R.id.titleChoices)
        val outTxt = findViewById<TextView>(R.id.outTxt)
        val listOfTitles = intent.getStringArrayExtra("listOfTitles")
        println("Titles : " + listOfTitles[0])
        val start = findViewById<Button>(R.id.getDataBtn)
        val output = findViewById<TextView>(R.id.outputTxt)


        if(choices != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfTitles)
            choices.adapter = adapter
            choices.setOnItemSelectedListener(object : OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    titleChosen = parent.getItemAtPosition(pos) as String
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
        }



        start.setOnClickListener {
            val inStream = File("$titleChosen.txt").inputStream()
            val inString = inStream.bufferedReader().use{it.readText()}
            if(inString != null)
                outTxt.text = inString;
            else
                outTxt.text = "Nothing available"
        }

    }
}
