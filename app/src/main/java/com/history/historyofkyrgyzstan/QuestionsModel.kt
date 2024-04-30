package com.history.historyofkyrgyzstan

data class QuestionsModel(val question:String, val optionA:String, val optionB:String, val optionC:String, val optionD:String, val correctAns:Int,
                          var selectedAns:Int)
