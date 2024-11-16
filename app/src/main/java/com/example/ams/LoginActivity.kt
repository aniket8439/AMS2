package com.example.ams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val loginButton: TextView = findViewById(R.id.Login_btn)
        loginButton.setOnClickListener {
            signInUser()


        }


        val registerButton: TextView = findViewById(R.id.text_view)
        registerButton.setOnClickListener {
            val toast = Toast.makeText(this, "welcome to the Register Section.", Toast.LENGTH_SHORT)
            toast.show()
            startActivity(Intent(this, RegisterActivity::class.java))

        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser: FirebaseUser?) {

    }


    private fun signInUser() {
        val email: EditText = findViewById(R.id.username_input)
        if (email.text.toString().isEmpty()) {
            email.error = "Please enter email"
            email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = "Please enter valid email"
            email.requestFocus()
            return
        }
        val password: EditText = findViewById(R.id.pass)
        if (password.text.toString().isEmpty()) {
            password.error = "Please enter password"
            password.requestFocus()
            return
        }


        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        "You are logged in successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent =
                        Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra(
                        "user_id",
                        FirebaseAuth.getInstance().currentUser!!.uid
                    )

                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()

                }

            }
    }
}





