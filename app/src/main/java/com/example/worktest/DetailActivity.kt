package com.example.worktest

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class DetailActivity : AppCompatActivity() {
    var j: Japan? = null
    lateinit var ps: List<Population>
    var id = 0
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        id = intent.getIntExtra("id", 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        jsonData
        setData()
    }

    val jsonData: Unit
        get() {
            val d = readJson("japan.json")
            j = Gson().fromJson(d, Japan::class.java)
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

    fun setData() {
        var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var cTdfk: Tdfk? = null
    for (tdfk in j!!.prefectures!!) {
            if (tdfk.id == id) {
                cTdfk = tdfk
                break
            }
        }
        if (cTdfk != null) {
            val chihou = findViewById<View>(R.id.chihou) as TextView
            for (r in j!!.regions!!) {
                if (r.id == cTdfk.region_id) {
                    chihou.text = r.name
                }
            }

            //            ロングタップ処理
            chihou.setOnLongClickListener {
                var clip = ClipData.newPlainText("", chihou.text)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(applicationContext,"${chihou.text}: をコピーしました", Toast.LENGTH_SHORT).show()
                false
            }

            val tdfkName = findViewById<View>(R.id.tdfk) as TextView
            tdfkName.text = cTdfk.name
            title = cTdfk.name

            //            ロングタップ処理
            tdfkName.setOnLongClickListener {
                var clip = ClipData.newPlainText("", tdfkName.text)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(applicationContext,"${tdfkName.text}: をコピーしました", Toast.LENGTH_SHORT).show()
                false
            }

            val kenchoshozaichi = findViewById<View>(R.id.kenchoshozaichi) as TextView
            kenchoshozaichi.text = cTdfk.capital!!.name

            val toshiType = findViewById<View>(R.id.toshitype) as TextView
            if (cTdfk.capital!!.city_type == 4) {
                toshiType.text = "（特別区）"
            } else if (cTdfk.capital!!.city_type == 3) {
                toshiType.text = "（政令指定都市）"
            } else if (cTdfk.capital!!.city_type == 2) {
                toshiType.text = "（施工時特例市）"
            } else if (cTdfk.capital!!.city_type == 1) {
                toshiType.text = "（中核都市）"
            } else {
                toshiType.text = "（−）"
            }

            val kenchoField = findViewById<View>(R.id.kenchofield)
            //            ロングタップ処理
            kenchoField.setOnLongClickListener {
                var clip = ClipData.newPlainText("", (kenchoshozaichi.text as String?)?.plus(toshiType.text))
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(applicationContext,"${(kenchoshozaichi.text as String?)?.plus(toshiType.text)}: をコピーしました", Toast.LENGTH_SHORT).show()
                false
            }


            val zip = findViewById<View>(R.id.yuubinbangou) as TextView
            zip.text = "〒" + cTdfk.capital!!.zip_code

            //            ロングタップ処理
            zip.setOnLongClickListener {
                var clip = ClipData.newPlainText("", zip.text)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(applicationContext,"${zip.text}: をコピーしました", Toast.LENGTH_SHORT).show()
                false
            }

            val add = findViewById<View>(R.id.juusho) as TextView
            val address = cTdfk.capital!!.address
            add.text = address

            //            ロングタップ処理
            add.setOnLongClickListener {
                var clip = ClipData.newPlainText("", add.text)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(applicationContext,"${add.text}: をコピーしました", Toast.LENGTH_SHORT).show()
                false
            }

            val map = findViewById<View>(R.id.openmap)
            map.setOnClickListener {
                val uri = Uri.parse("geo:0,0?q=$address")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }

            val menseki = findViewById<View>(R.id.menseki) as TextView
            menseki.text = cTdfk.area.toString() + "㎢"

            //            ロングタップ処理
            menseki.setOnLongClickListener {
                var clip = ClipData.newPlainText("", menseki.text)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(applicationContext,"${menseki.text}: をコピーしました", Toast.LENGTH_SHORT).show()
                false
            }

            val jinkoumitsudo = findViewById<View>(R.id.jinkoumitsudo) as TextView
            jinkoumitsudo.text = getMitsudo(cTdfk.id, cTdfk.area).toString() + "人"

            //            ロングタップ処理
            jinkoumitsudo.setOnLongClickListener {
                var clip = ClipData.newPlainText("", jinkoumitsudo.text)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(applicationContext,"${jinkoumitsudo.text}: をコピーしました", Toast.LENGTH_SHORT).show()
                false
            }

            val hana = findViewById<View>(R.id.hana) as TextView
            var f: String? = ""
            if (cTdfk.flowers != null) {
                for (flower in cTdfk.flowers!!) {
                    if (f!!.isNotEmpty()) {
                        f += "\n"
                    }
                    f += flower
                }
                hana.text = f
            }

            //            ロングタップ処理
            hana.setOnLongClickListener {
                var clip = ClipData.newPlainText("", hana.text)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(applicationContext,"${hana.text}: をコピーしました", Toast.LENGTH_SHORT).show()
                false
            }

            val ki = findViewById<View>(R.id.ki) as TextView
            var k: String? = ""
            for (tree in cTdfk.trees) {
                if (k!!.isNotEmpty()) {
                    k += "\n"
                }
                k += tree
            }
            ki.text = k

            //            ロングタップ処理
            ki.setOnLongClickListener {
                var clip = ClipData.newPlainText("", ki.text)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(applicationContext,"${ki.text}: をコピーしました", Toast.LENGTH_SHORT).show()
                false
            }
        } else {
            finish()
        }
    }

    fun getMitsudo(id: Int, area: Float): Float {
        populationData
        for (p in ps) {
            if (p.prefecture_id == id) {
                return p.value / area
            }
        }
        return 0F
    }

    val populationData: Unit
        get() {
            val d = readJson("population.json")
            ps = Gson().fromJson(d, Array<Population>::class.java).toList()
        }
}