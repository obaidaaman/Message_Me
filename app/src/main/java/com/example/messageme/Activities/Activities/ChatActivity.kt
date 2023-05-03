package com.example.messageme.Activities.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messageme.Activities.Adapter.MessageAdapter
import com.example.messageme.Activities.DataClass.Message
import com.example.messageme.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    private lateinit var messageAdapter: MessageAdapter

    private lateinit var messageList: ArrayList<com.example.messageme.Activities.DataClass.Message>

    private lateinit var mdbRef: DatabaseReference

    private lateinit var firebaseAuth: FirebaseAuth

    var receiverRoom: String? = null

    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val name = intent.getStringExtra("name")

        val receiverUserId = intent.getStringExtra("user_Id")

        supportActionBar?.title = name.toString()

        messageList = ArrayList()

        messageAdapter = MessageAdapter(this, messageList)

        binding.recyclerViewChatLog.layoutManager = LinearLayoutManager(this)

        binding.recyclerViewChatLog.adapter = messageAdapter
//
        val senderUserId = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = senderUserId + receiverUserId

        receiverRoom = receiverUserId + senderUserId

        mdbRef = FirebaseDatabase.getInstance().getReference()

        // logic for adding data to recycler view

        mdbRef.child("Chats").child(senderRoom!!).child("Messages")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshot in snapshot.children) {

                        val message: Message? =
                            postSnapshot.getValue(com.example.messageme.Activities.DataClass.Message::class.java)

                        messageList.add(message!!)

                    }


                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        binding.button.setOnClickListener {

            val message = binding.SendMessageText.text.toString()

            val messageObject =
                com.example.messageme.Activities.DataClass.Message(message, senderUserId)

            mdbRef.child("Chats").child(senderRoom!!).child("Messages").push()
                .setValue(messageObject).addOnCompleteListener {
                    mdbRef.child("Chats").child(receiverRoom!!).child("Messages").push()
                        .setValue(messageObject).addOnCompleteListener {

                        }
                }
            binding.SendMessageText.setText("")
        }
    }
}