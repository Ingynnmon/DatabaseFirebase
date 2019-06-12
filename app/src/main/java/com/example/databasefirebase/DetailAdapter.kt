package com.example.databasefirebase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class DetailAdapter(val mContext: Context,val layoutRestId: Int, val detailList: List<Detail>)
    :ArrayAdapter<Detail>(mContext, layoutRestId, detailList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(layoutRestId,null)

        val textViewName = view.findViewById<TextView>(R.id.textView_name)
        val textViewUpdate = view.findViewById<TextView>(R.id.textViewUpdate)

        val detail = detailList[position]

        textViewName.text = detail.name

        textViewUpdate.setOnClickListener{
            showUpdateDialog(detail)
        }

        return view
    }

    private fun showUpdateDialog(detail: Detail) {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Update Detail")

        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.layout_update_detail,null)
        val editText = view.findViewById<EditText>(R.id.name_et)
        val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)

        editText.setText(detail.name)
        ratingBar.rating=detail.rating.toFloat()
        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->
            val dbDetail = FirebaseDatabase.getInstance().getReference("details")

            val name = editText.text.toString().trim()

            if(name.isEmpty()){
                editText.error="Please enter a name"
                editText.requestFocus()
                return@setPositiveButton
            }

            val detail = Detail(detail.id,name,ratingBar.rating.toInt())
            dbDetail.child(detail.id).setValue(detail)
            Toast.makeText(mContext,"Detail Updated",Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { dialog, which ->
            //
        }

        val alert = builder.create()
        alert.show()
    }
}