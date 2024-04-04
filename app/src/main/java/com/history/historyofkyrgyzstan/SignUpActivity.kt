package com.history.historyofkyrgyzstan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var username: EditText
    lateinit var surname: EditText
    lateinit var email: EditText
    lateinit var pass: EditText
    lateinit var confirm_pass: EditText
    lateinit var backButton:ImageView
    lateinit var signUpButton:Button
    lateinit var auth:FirebaseAuth
    lateinit var emailStr:String
    lateinit var passwordStr:String
    lateinit var usernameStr:String
    lateinit var surnameStr:String
    lateinit var confirmPasswordStr:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        username=findViewById(R.id.username)
        surname=findViewById(R.id.userSecondName)
        email=findViewById(R.id.email_id)
        pass=findViewById(R.id.pass)
        confirm_pass=findViewById(R.id.confirm_pass)
        backButton=findViewById(R.id.backButton)
        signUpButton=findViewById(R.id.signUpButton)

        auth=FirebaseAuth.getInstance()
        backButton.setOnClickListener{
            finish()
        }
        signUpButton.setOnClickListener {
            if (validate()) {
                signUpNewUser()
            }
        }



    }
    private fun validate():Boolean{
        usernameStr=username.text.toString().trim()
        emailStr=email.text.toString().trim()
        surnameStr=surname.text.toString().trim()
        passwordStr=pass.text.toString().trim()
        confirmPasswordStr=confirm_pass.text.toString().trim()
        if (usernameStr.isEmpty()){
            username.setError("gde imya")
            return false
        }
        if (surnameStr.isEmpty()){
            surname.setError("напишите фамилию")
            return false
        }
        if (emailStr.isEmpty()){
            email.setError("напишите email")
            return false
        }
        if (passwordStr.isEmpty()){
            pass.setError("напишите пароль")
            return false
        }
        if (confirmPasswordStr.isEmpty()){
            confirm_pass.setError("повторите пароль")
            return false
        }
        if (confirmPasswordStr.compareTo(passwordStr)!=0){
            Toast.makeText(this@SignUpActivity,"пароли не совпадают",Toast.LENGTH_SHORT).show()
            return false
        }
        return true

    }
    private fun signUpNewUser(){
        auth.createUserWithEmailAndPassword(emailStr, passwordStr)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@SignUpActivity,"sign up successful",Toast.LENGTH_SHORT).show()
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    this@SignUpActivity.finish()
                } else {
                    // If sign in fails, display a message to the
                    Toast.makeText(
                        this@SignUpActivity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }
}