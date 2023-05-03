package com.example.messageme.Activities.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.messageme.Activities.DataClass.Message
import com.example.messageme.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) : RecyclerView.Adapter<ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
  val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent)
    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ReceiveMessage = itemView.findViewById<TextView>(R.id.txt_receive)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

if (viewType == 1){
    // inflate receive

    val itemView = LayoutInflater.from(context).inflate(R.layout.received,parent,false)
    return ReceiveViewHolder(itemView)
}else{
    //inflate sent

    val itemView = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
    return SentViewHolder(itemView)
}

    }

    override fun getItemCount(): Int {
       return messageList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == SentViewHolder::class.java){

       val viewHolder = holder as SentViewHolder

       holder.sentMessage.text= currentMessage.message


   }else{
       val viewHolder = holder as ReceiveViewHolder

       holder.ReceiveMessage.text= currentMessage.message
   }


    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return  ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }
}