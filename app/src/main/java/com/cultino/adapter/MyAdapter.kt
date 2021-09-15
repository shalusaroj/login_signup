package com.cultino.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cultino.Movie
import com.cultino.R
import com.squareup.picasso.Picasso


class MyAdapter(lst :List<Movie>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    private var myList = lst

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var cropid_: TextView
        lateinit var id_: TextView
        lateinit var districtid_: TextView
        lateinit var district_: TextView
        lateinit var hindiname_: TextView
        lateinit var image_: ImageView
        lateinit var km_: TextView
        lateinit var lastdate_:TextView
        lateinit var lat_:TextView
        lateinit var lng_:TextView
        lateinit var location_:TextView
        lateinit var market_:TextView
        lateinit var meters_:TextView
        lateinit var state_:TextView
        lateinit var urlstr_:TextView

        init {
            this.cropid_ = itemView.findViewById<View>(R.id.cropId_txt) as TextView
            this.id_ = itemView.findViewById<View>(R.id.id_txt) as TextView
            this.districtid_ = itemView.findViewById<View>(R.id.districtId_txt) as TextView
            this.district_ = itemView.findViewById<View>(R.id.district_txt) as TextView
            this.hindiname_ = itemView.findViewById<View>(R.id.hindi_name_txt) as TextView
            this.image_ = itemView.findViewById<View>(R.id.image) as ImageView
            this.km_ = itemView.findViewById<View>(R.id.km_txt) as TextView
            this.lastdate_ = itemView.findViewById<View>(R.id.last_date_text) as TextView
            this.lat_ = itemView.findViewById<View>(R.id.lat_text) as TextView
            this.lng_ = itemView.findViewById<View>(R.id.lng_text) as TextView
            this.location_ = itemView.findViewById<View>(R.id.location_txt) as TextView
            this.market_ = itemView.findViewById<View>(R.id.market_text) as TextView
            this.meters_ = itemView.findViewById<View>(R.id.meters_text) as TextView
            this.state_ = itemView.findViewById<View>(R.id.state_text) as TextView
            this.urlstr_ = itemView.findViewById<View>(R.id.url_str_text) as TextView



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val CropID= myList[position].crop_id
        val ID = myList[position].id
        val District_ID = myList[position].district_id
        val District = myList[position].district
        val HindiName = myList[position].hindi_name
        val Image = myList[position].image
        val KM = myList[position].km
        val LastDate = myList[position].last_date
        val Lat = myList[position].lat
        val Lng = myList[position].lng
        val LOcation = myList[position].location
        val Market = myList[position].market
        val Meters = myList[position].meters
        val State = myList[position].state
        val UrlStr = myList[position].url_str

        holder.cropid_.text= CropID.toString()
        holder.id_.text=ID.toString()
        holder.districtid_.text=District_ID.toString()
        holder.district_.text=District
        holder.hindiname_.text=HindiName
        holder.km_.text=KM.toString()
        holder.lastdate_.text=LastDate
        holder.lat_.text=Lat.toString()
        holder.lng_.text=Lng.toString()
        holder.location_.text=LOcation
        holder.market_.text=Market
        holder.meters_.text=Meters.toString()
        holder.state_.text=State
        holder.urlstr_.text=UrlStr
        Picasso.get().load(Image).into(holder.image_)


    }

    fun setData(newList: List<Movie>){
        myList = newList
        notifyDataSetChanged()
    }
}