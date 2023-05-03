package com.example.messageme.Activities.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messageme.Activities.Adapter.Adapter
import com.example.messageme.Activities.DataClass.UserDetails
import com.example.messageme.databinding.ActivityNewMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class NewMessageActivity : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference

    private lateinit var userArrayList: MutableList<UserDetails>

    private lateinit var binding: ActivityNewMessageBinding

    private lateinit var adapter: Adapter

    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title= "Select User"

        firebaseAuth = FirebaseAuth.getInstance()


        binding.recyclerViewNewMessageActivity.layoutManager= LinearLayoutManager(this)
            binding.recyclerViewNewMessageActivity.setHasFixedSize(true)

        userArrayList= arrayListOf<UserDetails>()
        getUserData()

 //       binding.recyclerViewNewMessageActivity.adapter =
    }

    private fun getUserData() {
        dbRef = FirebaseDatabase.getInstance().getReference("User_Id")

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userArrayList.clear()
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(UserDetails::class.java)

                      if (firebaseAuth.currentUser?.uid != user?.userId){
                          userArrayList.add(user!!)
                      }

                    }

                    val mAdapter = Adapter(this@NewMessageActivity,userArrayList as ArrayList<UserDetails>)
                    binding.recyclerViewNewMessageActivity.adapter=mAdapter

                    mAdapter.notifyDataSetChanged()
                   // binding.recyclerViewNewMessageActivity.adapter= Adapter(userArrayList)

//                mAdapter.setOnItemClickListener(object : Adapter.onItemClickListener{
//
//                    override fun onItemClick(position: Int) {
//
//                        val intent = Intent(this@NewMessageActivity, ChatActivity::class.java)
//                        intent.putExtra("User_Id",userArrayList[position].userId)
//                        intent.putExtra("User_Name",userArrayList[position].fullName)
//
//                        startActivity(intent)
//                        finish()
//                    }
//
//                })

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}