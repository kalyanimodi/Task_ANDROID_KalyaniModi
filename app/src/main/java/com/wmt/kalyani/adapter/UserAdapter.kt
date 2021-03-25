package com.wmt.kalyani.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wmt.kalyani.R
import com.wmt.kalyani.model.UserResModel
import com.wmt.kalyani.view.HomeActivity

class UserAdapter(
    val context: Context,
    val list: ArrayList<UserResModel.DataList>
) : RecyclerView.Adapter<UserAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.txt_username!!.text = "Username : "+list.get(position).username
        holder.txt_email!!.text = "Email ID : "+list.get(position).email
        Glide.with(holder.imgView_icon!!.context)
            .load(list.get(position).profile_pic)
            .into(holder.imgView_icon!!)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgView_icon: ImageView? = null
        var txt_username: TextView? = null
        var txt_email: TextView? = null

        init {
            imgView_icon = itemView.findViewById(R.id.img_profile)
            txt_username = itemView.findViewById(R.id.txt_username)
            txt_email = itemView.findViewById(R.id.txt_email)
        }
    }
    fun addList(list_data: ArrayList<UserResModel.DataList>) {
        list.addAll(list_data)
        notifyDataSetChanged()
    }

}