package com.own_world.chatapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.own_world.chatapp.R
import com.own_world.chatapp.Unit.User

class UserAdapter(val context: Context, val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user, parent, false)
        return  UserViewHolder(view)
}
    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.textName.text = user.name

    }

    class UserViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.user_Name)
    }
}