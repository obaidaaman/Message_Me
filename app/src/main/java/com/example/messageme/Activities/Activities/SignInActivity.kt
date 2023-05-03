package com.example.messageme.Activities.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.messageme.Activities.MainActivity
import com.example.messageme.R
import com.example.messageme.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()
        dbRef= FirebaseDatabase.getInstance().getReference("User_Id")
        binding.loginButton.setOnClickListener {

            val email= binding.email.text.toString().lowercase()
            val pass = binding.password.text.toString().lowercase()

            if (email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{

                    if (it.isSuccessful){

                        val intent = Intent(this@SignInActivity, LatestMessagesActivity::class.java)


                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                        startActivity(intent)
                    }

                    else{
                        Toast.makeText(this,"User Not Found", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        binding.signUp.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))


        }
    }
    override fun onStart() {
        super.onStart()


        if (FirebaseAuth.getInstance().currentUser!=null){
            startActivity(Intent(this@SignInActivity,LatestMessagesActivity::class.java))
            finish()
        }

        }
    }
