package com.history.historyofkyrgyzstan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.history.historyofkyrgyzstan.MyDb.loadQuestions
import com.history.historyofkyrgyzstan.MyDb.testList
import com.history.historyofkyrgyzstan.databinding.ActivityStartTestBinding

class StartTestActivity : AppCompatActivity() {
    private lateinit  var binding: ActivityStartTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title="Тест"
        }
        loadQuestions(object :MyCompleteListener{
            override fun OnSucces() {
                setData()
            }

            override fun OnFailure() {
                TODO("Not yet implemented")
            }
        }
        )
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Handle back button press
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun init(){

        binding.startTest.setOnClickListener{
            val intent= Intent(this@StartTestActivity,QuestionsActivity::class.java)
            startActivity(intent)
            finish()

        }

    }
    private fun setData(){
        binding.stTestNo.text = "Тест ${MyDb.g_selected_test+1 }"
        binding.stTestName.text = testList[MyDb.g_selected_test].testName
        binding.stTotalQuestion.text="${MyDb.questionsList.size}"
        binding.stBestScore.text = "${testList[MyDb.g_selected_test].topScore}"


    }
}