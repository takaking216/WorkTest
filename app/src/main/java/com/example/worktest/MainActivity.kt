package com.example.worktest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {
    var j: Japan? = null
    var a: TdfkAdapter? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "都道府県一覧"
    }

    override fun onResume() {
        super.onResume()
        jsonData
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_ask -> {
                Collections.sort(j!!.prefectures, IdComparator())
                a!!.notifyDataSetChanged()
            }
            R.id.area_desc -> {
                Collections.sort(j!!.prefectures, AreaComparator())
                a!!.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    val jsonData: Unit
        get() {
            val d = readJson("japan.json")
            j = Gson().fromJson(d, Japan::class.java)
            val rv = findViewById<RecyclerView>(R.id.rv)
            rv.layoutManager = LinearLayoutManager(this)
            a = TdfkAdapter(this, j!!.regions, j!!.prefectures)
            rv.adapter = a
            a!!.notifyDataSetChanged()
        }

    fun readJson(fileName: String?): String? {
        val am = resources.assets
        try {
            val `is` = am.open(fileName!!)
            val br = BufferedReader(InputStreamReader(`is`))
            var l: String?
            val sb = StringBuilder()
            while (br.readLine().also { l = it } != null) {
                sb.append(l)
            }
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}