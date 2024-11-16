package com.example.ams


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ams.databinding.ActivityRegisterBinding
import com.example.ams.databinding.ActivityRegisterBinding.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()


        val registerButton: Button = findViewById(R.id.cirRegisterButton)
        registerButton.setOnClickListener {
            signUpUser()


        }
    }
    private fun  signUpUser(){
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        if(editTextEmail.text.toString().isEmpty()){
            editTextEmail.error = "Please enter email"
            editTextEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()){
            editTextEmail.error = "Please enter valid email"
            editTextEmail.requestFocus()
            return
        }
        val password : EditText = findViewById(R.id.editTextPassword)
        if(password.text.toString().isEmpty()){
            password.error = "Please enter password"
            password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                } else {
                   Toast.makeText(baseContext,"Sign up failed.",
                       Toast.LENGTH_SHORT).show()
                }
            }
    }
}