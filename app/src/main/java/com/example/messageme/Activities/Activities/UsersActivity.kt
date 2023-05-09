package com.example.messageme.Activities.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messageme.Activities.Adapter.Adapter
import com.example.messageme.Activities.model.UserDetails
import com.example.messageme.databinding.ActivityUsersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UsersActivity : AppCompatActivity() {
    var userList = ArrayList<UserDetails>()
    private lateinit var binding: ActivityUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)

        getUsersList()
    }

    fun getUsersList() {
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

        var userid = firebase.uid
    //    FirebaseMessaging.getInstance().subscribeToTopic("/topics/$userid")


        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users")


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val user = dataSnapShot.getValue(UserDetails::class.java)

                    if (!user!!.userId.equals(firebase.uid)) {

                        userList.add(user)
                    }
                }

                val userAdapter = Adapter(this@UsersActivity, userList)

                binding.userRecyclerView.adapter = userAdapter
            }

        })
    }
}