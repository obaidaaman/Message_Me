package com.example.messageme.Activities.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.messageme.Activities.DataClass.UserDetails
import com.example.messageme.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    // Firebase instance variables

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var dbRef: DatabaseReference

    private lateinit var storageReference: FirebaseStorage

    private lateinit var uri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageReference = FirebaseStorage.getInstance()

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.selectPhoto.setImageURI(it)
                if (it != null) {
                    uri = it
                }
            })



        firebaseAuth = FirebaseAuth.getInstance()

        dbRef = FirebaseDatabase.getInstance().getReference("User_Id")




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

                if (confirmpass == pass) {
                    val progressDialog = ProgressDialog(this@SignUpActivity)
                    progressDialog.setTitle("SignUp")
                    progressDialog.setMessage("Please wait, This may take a while...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()


                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val userId = dbRef.push().key!!

                            uploadImageToFirebase()

                            val userDetails = UserDetails(userId, name, email, pass,)

                            dbRef.child(userId).setValue(userDetails)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        progressDialog.dismiss()

                                    }
                                }

                            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LatestMessagesActivity::class.java))
                            finish()

                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            firebaseAuth.signOut()

                            progressDialog.dismiss()
                        }


                    }

                    // Initialize Firebase Auth and check if the user is signed in
//
//                if (firebaseAuth.currentUser == null) {
//                    // Not signed in, launch the Sign In activity
//                    startActivity(Intent(this, SignInActivity::class.java))
//                    finish()
//
//                }
                } else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Empty fields not allowed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.appLogo.setOnClickListener {


            loadImage.launch("image/*")
        }

        binding.btnSignIn.setOnClickListener {
//         val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
//
//            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
            finish()
        }
    }

    private fun uploadImageToFirebase() {

        storageReference.getReference("Images").child(System.currentTimeMillis().toString())
            .putFile(uri)
            .addOnSuccessListener { task ->
                task.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        val uid = FirebaseAuth.getInstance().currentUser!!.uid

                        val imageMap = mapOf(
                            "url" to uri
                        )

                        val databaseReference = FirebaseDatabase.getInstance().getReference("User_Id")
                        databaseReference.child(uid).setValue(imageMap)


                    }


            }

    }
}

