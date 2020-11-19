package com.example.worktest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.worktest.TdfkAdapter.TdfkViewHolder

class TdfkAdapter(var context: Context, var cList: List<Chihou?>?, var tList: List<Tdfk?>?) :
    RecyclerView.Adapter<TdfkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TdfkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_tdfk, parent, false)
        return TdfkViewHolder(view)
    }

    override fun onBindViewHolder(holder: TdfkViewHolder, p: Int) {
        val t = tList!![p]
        holder.chihou.text = getChihoumei(t!!.region_id)
        holder.tdfk.text = t.name
        holder.menseki.text = "" + t.area + "㎢"
        holder.kenchoshozaichi.text = "（" + t.capital!!.name + "）"
        holder.kugiri.visibility = if (itemCount == p + 1) View.GONE else View.VISIBLE
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", t.id)
            context.startActivity(intent)
        }
    }

    fun getChihoumei(region_id: Int): String? {
        for (c in cList!!) {
            if (c!!.id == region_id) return c.name
        }
        return null
    }

    override fun getItemCount(): Int {
        return tList!!.size
    }

    class TdfkViewHolder(view: View) : ViewHolder(view) {
        var chihou: TextView
        var tdfk: TextView
        var menseki: TextView
        var kenchoshozaichi: TextView
        var kugiri: View

        init {
            chihou = view.findViewById(R.id.chihou)
            tdfk = view.findViewById(R.id.tdfk)
            menseki = view.findViewById(R.id.menseki)
            kenchoshozaichi = view.findViewById(R.id.tdfk_symbol)
            kugiri = view.findViewById(R.id.kugirisen)
        }
    }
}