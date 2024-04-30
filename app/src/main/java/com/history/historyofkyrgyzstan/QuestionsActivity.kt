package com.history.historyofkyrgyzstan

import com.history.historyofkyrgyzstan.R;
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.history.historyofkyrgyzstan.databinding.ActivityQuestionsBinding
import com.history.historyofkyrgyzstan.MyDb.loadQuestions
import com.history.historyofkyrgyzstan.MyDb.questionsList


class QuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionsBinding
    var position:Int=0
    var allScore=0
    private var optionSelected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setData(position)
        binding.nextButton.setOnClickListener {
            optionSelected = false

            // Increment the current question index
            position++


            // If there are more questions remaining, display the next question and options
            if (position < questionsList.size) {
                setData(position)

            } else {
                val intent= Intent(this@QuestionsActivity,ScoreActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        binding.optionA.setOnClickListener {
            if (!optionSelected) { // Check if an option has already been selected
                optionSelected = true
                selectOption(binding.optionA, 1, position)
            }
        }
        binding.optionB.setOnClickListener {
            if (!optionSelected) {
                optionSelected = true
                selectOption(binding.optionB, 2, position)
            }
        }
        binding.optionC.setOnClickListener {
            if (!optionSelected) {
                optionSelected = true
                selectOption(binding.optionC, 3, position)
            }
        }
        binding.optionD.setOnClickListener {
            if (!optionSelected) {
                optionSelected = true
                selectOption(binding.optionD, 4, position)
            }
        }


    }
    private fun selectOption(btn: Button, option_num: Int, quesID:Int){

        // Reset background color of all options

        questionsList[quesID].selectedAns=option_num
        val correctAnswer = questionsList[quesID].correctAns

        if (option_num == correctAnswer) {
            // Correct answer: Change background color of selected option to green
            btn.setBackgroundColor(getColor(R.color.correct))
        } else {
            // Incorrect answer: Change background color of selected option to red
            btn.setBackgroundColor(getColor(R.color.uncorrect))

            // Change background color of correct option to green
            when (correctAnswer) {
                1 -> binding.optionA.setBackgroundColor(getColor(R.color.correct))
                2 -> binding.optionB.setBackgroundColor(getColor(R.color.correct))
                3 -> binding.optionC.setBackgroundColor(getColor(R.color.correct))
                4 -> binding.optionD.setBackgroundColor(getColor(R.color.correct))
            }
        }
    }
    private fun setData(pos:Int){
        val progress = ((position.toFloat() +1)/ questionsList.size.toFloat()) * 100
        binding.progressBar.progress= progress.toInt()

        binding.tvQuestionNumber.text = "${position + 1}/${questionsList.size}"
        binding.optionA.setBackgroundColor(getColor(R.color.button))
        binding.optionB.setBackgroundColor(getColor(R.color.button))
        binding.optionC.setBackgroundColor(getColor(R.color.button))
        binding.optionD.setBackgroundColor(getColor(R.color.button))
        binding.textOfQuestion.text = questionsList[pos].question
        binding.optionA.text=questionsList[pos].optionA
        binding.optionB.text=questionsList[pos].optionB
        binding.optionC.text=questionsList[pos].optionC
        binding.optionD.text=questionsList[pos].optionD
        if (position == questionsList.size - 1) {
            binding.nextButton.text = getString(R.string.finish) // Change text to "Finish"
        } else {
            binding.nextButton.text = getString(R.string.next)// Change text back to "Next" for other questions
        }
    }


}