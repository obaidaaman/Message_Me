package com.example.messageme.Activities.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messageme.Activities.Activities.ChatActivity
import com.example.messageme.Activities.model.UserDetails
import com.example.messageme.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView

class Adapter(private val context: Context, private val contactList: ArrayList<UserDetails>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

  //  private lateinit var mListener : onItemClickListener

//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//    }
// fun setOnItemClickListener(clickListener: onItemClickListener){
//     mListener= clickListener
// }
private var firebaseUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)

        return ViewHolder(itemView)
    }


    override fun getItemCount(): Int {
      return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentItem =  contactList[position]
        holder.name.text = currentItem.fullName

        holder.layoutUser.setOnClickListener{
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("name", currentItem.fullName)
            intent.putExtra("user_Id", currentItem.userId)
            context.startActivity(intent)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.userName)
      //  val image :CircleImageView = itemView.findViewById(R.id.circleImageView)
        val layoutUser : LinearLayout = itemView.findViewById(R.id.layoutUser)
//        init {
//            itemView.setOnClickListener{
//                clickListener.onItemClick(adapterPosition)
//            }
        }
    }

