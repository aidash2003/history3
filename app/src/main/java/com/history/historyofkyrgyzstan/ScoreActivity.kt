package com.history.historyofkyrgyzstan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.history.historyofkyrgyzstan.MyDb.questionsList
import com.history.historyofkyrgyzstan.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding:ActivityScoreBinding
    private  var score:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        binding.buttonReattempt.setOnClickListener{
            reAttempt()
        }
        binding.buttonGoHome.setOnClickListener {
            val intent=Intent(this@ScoreActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        saveResult()
    }

    private fun loadData() {
        var correct = 0
        var wrong = 0
        var unattempt = 0

        // Loop through questionsList to calculate correct, wrong, and unattempted questions
        for (i in 0 until questionsList.size) {


            if (questionsList[i].selectedAns == -1) {
                unattempt=unattempt+1
            } else if (questionsList[i].selectedAns == questionsList[i].correctAns) {
                correct=correct+1
            } else {
                wrong++
            }
        }

        // Calculate the score as a percentage
        val totalQuestions =questionsList.size
        score = (correct * 100) / totalQuestions

        // Update TextViews with correct, wrong, and score values
        binding.correct.text = correct.toString()
        binding.wrong.text = wrong.toString()
        binding.unattempted.text=unattempt.toString()
        binding.result.text = score.toString()
        binding.progressBar.progress=score

    }

    private fun reAttempt(){
        for (i in 0 until questionsList.size ){
            questionsList[i].selectedAns=-1}
        val intent=Intent(this@ScoreActivity,StartTestActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun saveResult() {
        MyDb.saveResult(score, object : MyCompleteListener {
            override fun OnSucces() {
                Toast.makeText(this@ScoreActivity,"saved result", Toast.LENGTH_SHORT).show()
            }

            override fun OnFailure() {
                Toast.makeText(this@ScoreActivity,"unsaved result", Toast.LENGTH_SHORT).show()
            }
        })
    }


}

