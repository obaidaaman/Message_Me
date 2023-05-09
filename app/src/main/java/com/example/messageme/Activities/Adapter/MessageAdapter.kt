package com.example.messageme.Activities.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.messageme.Activities.model.Message
import com.example.messageme.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase



class MessageAdapter(var context: Context, var messageList: ArrayList<Message>) : RecyclerView.Adapter<ViewHolder>() {

    val firebaseDatabase : FirebaseDatabase? = null

    val ITEM_RECEIVE = 2
    val ITEM_SENT = 1
    var senderRoom : String? = null
    var ReceiverRoom : String? = null
    fun setRooms(senderRoom: String?, receiverRoom: String?) {
        this.senderRoom = senderRoom
        this.ReceiverRoom = receiverRoom
    }
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
  val sentMessage: TextView = itemView.findViewById(R.id.txt_sent)
    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ReceiveMessage: TextView = itemView.findViewById(R.id.txt_receive)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

if (viewType == ITEM_RECEIVE){
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

       viewHolder.sentMessage.text= currentMessage.message


   }else{
       val viewHolder = holder as ReceiveViewHolder

       viewHolder.ReceiveMessage.text= currentMessage.message
   }

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage : Message = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return  ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }
    fun addMessage(message: Message) {
        messageList.add(message)
        notifyItemInserted(messageList.size - 1)
    }
}