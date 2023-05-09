package com.example.messageme.Activities.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.messageme.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    // Firebase instance variables

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var databaseReference: DatabaseReference

    private lateinit var storageReference: FirebaseStorage

    private lateinit var uri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageReference = FirebaseStorage.getInstance()





        firebaseAuth = FirebaseAuth.getInstance()






        binding.registerButton.setOnClickListener {
            val name = binding.registerName.text.toString().lowercase()
            val email = binding.registerEmail.text.toString().lowercase()
            val pass = binding.registerPassword.text.toString().lowercase()
            val confirmpass = binding.registerConfirmPassword.text.toString().lowercase()



            if (name.isEmpty()) {
                binding.registerName.error = "Please Enter Your Full Name"
            }
            if (email.isEmpty()) {
                binding.registerEmail.error = "Please Enter Your email"
            }
            if (pass.isEmpty()) {
                binding.registerPassword.error = "Please Enter Your password"
            }
            if (confirmpass.isEmpty()) {
                binding.registerConfirmPassword.error = "Please Enter the confirm password"
            }
            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()) {

                if (!pass.equals(confirmpass)){
                    Toast.makeText(applicationContext,"password not match",Toast.LENGTH_SHORT).show()
                }
                registerUser(name,email,pass)


            }


        }
        binding.btnSignIn.setOnClickListener {
//         val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
//
//            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
            finish()
        }

    }


//    }


    private fun registerUser(userName: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user: FirebaseUser? = firebaseAuth.currentUser
                    val userId: String = user!!.uid

                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["userId"] = userId
                    hashMap["userName"] = userName


                    databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            //open home activity
                            binding.registerName.setText("")
                            binding.registerEmail.setText("")
                            binding.registerPassword.setText("")
                            binding.registerConfirmPassword.setText("")
                            val intent = Intent(
                                this@SignUpActivity,
                                UsersActivity::class.java
                            )
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
    }
}

