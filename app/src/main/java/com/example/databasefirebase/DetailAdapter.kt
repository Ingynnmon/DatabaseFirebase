package com.example.databasefirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class DetailAdapter(val mContext: Context,val layoutRestId: Int, val detailList: List<Detail>)
    :ArrayAdapter<Detail>(mContext, layoutRestId, detailList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(layoutRestId,null)

        val textViewName = view.findViewById<TextView>(R.id.textView_name)

        val detail = detailList[position]

        textViewName.text = detail.name

        return view
    }
}