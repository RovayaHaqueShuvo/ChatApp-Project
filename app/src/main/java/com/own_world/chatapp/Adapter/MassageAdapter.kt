package com.own_world.chatapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.own_world.chatapp.Model.Massage
import com.own_world.chatapp.R

class MassageAdapter(val context: Context, val massageList: ArrayList<Massage>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            //inflate receive
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceivedViewHolder(view)
        }else{
            //inflate sent
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return massageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMassage = massageList[position]

        if (holder.javaClass == SentViewHolder::class.java){
            //do the stuff for sent view holder


            val viewHolder = holder as SentViewHolder
            holder.sentMassage.text = currentMassage.massage
        }
        else{
            //do the stuff for received view holder
            val viewHolder = holder as ReceivedViewHolder
            holder.receivedMassage.text = currentMassage.massage
        }
    }
    override fun getItemViewType(position: Int): Int {
        val currentMassage = massageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMassage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }


    class SentViewHolder(itemView: View):RecyclerView.ViewHolder (itemView){
        val sentMassage = itemView.findViewById<TextView>(R.id.txt_sent_massage)
    }
    class ReceivedViewHolder(itemView: View):RecyclerView.ViewHolder (itemView){
        val receivedMassage = itemView.findViewById<TextView>(R.id.txt_recive_massage)
    }
}