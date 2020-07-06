package com.calderon.midinero

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context : Context, val regs: ArrayList<Registro>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return regs.size
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.total.text = regs[position].total.toString()
        holder.fecha.text = getMonth(regs[position].fecha)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val total = itemView.findViewById<TextView>(R.id.tvTotal)
        val fecha = itemView.findViewById<TextView>(R.id.tvFecha)
    }


    fun getMonth(fecha:String): String{
        var f = fecha.split("/")
        var month = ""
        when (f[1].toInt()){
            1 ->month = "Enero"
            2 ->month = "Febrero"
            3 ->month = "Marzo"
            4 ->month = "Abril"
            5 ->month = "Mayo"
            6 ->month = "Junio"
            7 ->month = "julio"
            8 ->month = "Agosto"
            9 ->month = "Septiembre"
            10 ->month ="Octubre"
            11 ->month ="Noviembre"
            12 ->month ="Diciembre"
        }
        return f[0]+"/"+month+"/"+f[2]
    }

}