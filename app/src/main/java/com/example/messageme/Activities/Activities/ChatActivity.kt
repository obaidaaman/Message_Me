
package com.example.messageme.Activities.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messageme.Activities.Adapter.Adapter
import com.example.messageme.Activities.Adapter.MessageAdapter
import com.example.messageme.Activities.model.Message
import com.example.messageme.Activities.model.UserDetails
import com.example.messageme.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    private lateinit var messageAdapter: Adapter

    private lateinit var messageList: ArrayList<UserDetails>

    private lateinit var mdbRef: DatabaseReference

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var senderUserId: String

    private lateinit var receiverUserId: String

      var firebaseUser: FirebaseUser? = null

    var receiverRoom: String? = null

    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.let {
            senderUserId = it.uid
        }

        mdbRef = FirebaseDatabase.getInstance().getReference()

        val name = intent.getStringExtra("name")
        receiverUserId = intent.getStringExtra("user_Id")!!
        supportActionBar?.title = name.toString()

        messageList = ArrayList()
        messageAdapter = Adapter(this, messageList)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter





            senderRoom = senderUserId + receiverUserId
            receiverRoom = receiverUserId + senderUserId




        // logic for adding data to recycler view

//        mdbRef.child("Chats").child(senderRoom!!).child("Messages")
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    messageList.clear()
//                    for (postSnapshot in snapshot.children) {
//                        val message: Message? = postSnapshot.getValue(Message::class.java)
//                        message?.let {
//                            messageList.add(it)
//                        }
//                    }
//                    messageAdapter.notifyDataSetChanged()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Handle error
//                }
//            })

        binding.button.setOnClickListener {
            val message = binding.sendMessageText.text.toString()
            if (message.isNotEmpty()) {

                sendMessage(firebaseUser!!.uid, receiverUserId, message)
                            }else{
                Toast.makeText(this, "Message is Empty", Toast.LENGTH_SHORT).show()
            }
            
                    }
                binding.sendMessageText.setText("")
            }


    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat").push().setValue(hashMap)

    }
}