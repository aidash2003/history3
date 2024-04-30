package com.history.historyofkyrgyzstan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.history.historyofkyrgyzstan.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            if (validateData()){
                login()
            }

        }
        binding.signUpp.setOnClickListener{
            val intent= Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        auth=FirebaseAuth.getInstance()
        if (auth.currentUser!=null){

            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
    private fun validateData(): Boolean {
        if(binding.email.text.toString().trim().isEmpty()){
            binding.email.setError("Enter Email")
        return false}
        if(binding.password.text.toString().trim().isEmpty()){
            binding.password.setError("Enter Password")
            return false}
        return true
    }
    private fun login(){
        auth.signInWithEmailAndPassword(binding.email.text.toString().trim(), binding.password.text.toString().trim())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@LoginActivity,"login success", Toast.LENGTH_SHORT).show()
                    MyDb.loadUserData(object :MyCompleteListener{
                        override fun OnSucces() {
                            val intent= Intent(this@LoginActivity,MainActivity::class.java)
                            startActivity(intent)
                            this@LoginActivity.finish()
                        }

                        override fun OnFailure() {
                            Toast.makeText(this@LoginActivity, "Failed to load data", Toast.LENGTH_LONG).show()
                        }
                    })
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    this@LoginActivity.finish()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        this@LoginActivity,
                        task.exception?.message,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}