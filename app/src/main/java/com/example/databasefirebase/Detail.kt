package com.example.databasefirebase

data class Detail(val id: String,val name: String, val rating: Int){
    constructor() : this("","",0)
}