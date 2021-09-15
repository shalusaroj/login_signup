package com.cultino

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cultino.adapter.MyAdapter

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
    internal lateinit var recyclerView: RecyclerView
    lateinit var sharedPreferences: SharedPreferences
    internal lateinit var manager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        var bt=findViewById<Button>(R.id.fetd) as Button
       bt.setOnClickListener{
           val apiInterface = ApiInterface.create().getMovies()
           apiInterface.enqueue(object :Callback<JsonObject>{
               override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                   if(response?.body() != null){
                       var a= response.body()
                       var k= a?.get("data")
                       var l= k?.asJsonObject

                       var p= l?.get("other_mandi")

                       val gson = GsonBuilder().create()
                       val packagesArray = gson.fromJson(p , Array<Movie>::class.java).toList()
                       editor.putString("list",packagesArray.toString())
                       editor.apply()
                       recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
                       recyclerView.setHasFixedSize(true)
                       manager = LinearLayoutManager(this@MainActivity2)
                       recyclerView.layoutManager = manager

                       val recyclerAdapterItemInfo = MyAdapter(packagesArray)
                       recyclerView.adapter = recyclerAdapterItemInfo

                   }
               }

               override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                   Log.d("#111","Error : ${t}")
               }

           })
       }
    }


}