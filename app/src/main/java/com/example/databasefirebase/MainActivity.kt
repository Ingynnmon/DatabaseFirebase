package com.example.databasefirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var name_et: EditText
    lateinit var rating_bar: RatingBar
    lateinit var save_btn: Button
    lateinit var reference: DatabaseReference
    lateinit var listView: ListView

    lateinit var ref:DatabaseReference
    lateinit var detailList: MutableList<Detail>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        detailList = mutableListOf()
        reference = FirebaseDatabase.getInstance().getReference("details")

        name_et = findViewById(R.id.name_et)
        rating_bar = findViewById(R.id.rating_bar)
        save_btn = findViewById(R.id.save_btn)
        listView = findViewById(R.id.listView)

        save_btn.setOnClickListener {
            saveDetail()
        }

        reference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
               if(p0.exists()){
                   detailList.clear()
                   for(i in p0.children){
                       val detail = i.getValue(Detail::class.java)
                       detailList.add(detail!!)
                   }
                   val adapter = DetailAdapter(this@MainActivity, R.layout.details,detailList)
                   listView.adapter = adapter
               }
            }

        })
    }


    private fun saveDetail() {
        val hero_name = name_et.text.toString().trim()

        if(hero_name.isEmpty()){
            name_et.error="Please enter a name"
            return
        }

        reference = FirebaseDatabase.getInstance().getReference("details")
        val detailId =reference.push().key

        //val detail = Detail(detailId!!, hero_name,rating_bar.numStars)
        val detail = Detail(detailId!!, hero_name,rating_bar.rating.toInt())
        reference.child(detailId).setValue(detail).addOnCompleteListener {
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
        }

    }
}
