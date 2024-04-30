package com.history.historyofkyrgyzstan

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
import android.util.ArrayMap
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.WriteBatch
import java.util.Objects

object MyDb {
    var g_firestore: FirebaseFirestore? = null
    //aserdtfyguhj12 13
    //var testList: ArrayList<my_class>?=null
    public lateinit var testList: ArrayList<my_class>
    var g_selected_test=0
    public lateinit var testUrlList: ArrayList<String>
    public lateinit var questionsList: ArrayList<QuestionsModel>
    var my_account: AccountModel=AccountModel(" ","","")
    fun createmyUserData(email: String, name: String, surname: String,completeListener:MyCompleteListener) {
        val userData: MutableMap<String, Any> = ArrayMap()
        userData["EMAIL_ID"] = email
        userData["NAME"] = name
        userData["SURNAME"] = surname
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userDoc = g_firestore!!.collection("USERS").document(currentUser!!.uid)
        val batch = g_firestore!!.batch()
        batch[userDoc] = userData
        val countDoc = g_firestore!!.collection("USERS").document("TOTAL_USERS")
        batch.update(countDoc, "COUNT", FieldValue.increment(1))
        batch.commit()
            .addOnSuccessListener {completeListener.OnSucces() }
            .addOnFailureListener {completeListener.OnFailure() }
    }
    public fun getUserData(completeListener: MyCompleteListener) {
        g_firestore?.collection("USERS")?.document(FirebaseAuth.getInstance().uid!!)
            ?.get()
            ?.addOnSuccessListener { documentSnapshot ->

                my_account.name = documentSnapshot.getString("NAME")!!
                my_account.email = documentSnapshot.getString("EMAIL_ID")!!
                my_account.surname = documentSnapshot.getString("SURNAME")!!
                completeListener.OnSucces()
            }
            ?.addOnFailureListener { completeListener.OnFailure() }
    }
    public fun loadUserData(completeListener: MyCompleteListener){
        loadTests(object:MyCompleteListener{
            override fun OnSucces() {

                getUserData(completeListener)
            }

            override fun OnFailure() {
                completeListener.OnFailure()
            }
        })

    }

    fun loadTests(completeListener: MyCompleteListener) {
        testList = ArrayList()
        testList.clear()
        Log.d("TestList", "Size before loading data: ${testList.size}")
        g_firestore?.collection("Tests")?.document("TEST_INFO")?.get()
            ?.addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    Log.d("Firestore", "DocumentSnapshot is not null")}
                val testData = documentSnapshot?.data // Check if documentSnapshot is null
                testData?.let {
                    Log.d("TestList", "Size here loading data: ${testList.size}")
                    for (i in 1..7) {
                        val testNameKey = "TEST${i}_NAME" // Adjust key construction here
                        val testName = testData[testNameKey] as? String ?: ""
                        val testIDKey = "TEST${i}_ID" // Adjust key construction here
                        val testID = testData[testIDKey] as? String ?: ""
                        testList.add(my_class(testID,i-1, testName, 0))
                    }
                }
                completeListener.OnSucces() // Notify listener of success
            }
            ?.addOnFailureListener {
                // Notify listener of failure and pass the exception
                completeListener.OnFailure()
            }



    }
    fun openAndDownloadPdf(testID: Int, context: Context) {
        // Get the PDF URL for the given test ID
        g_firestore?.collection("Tests")?.document("TEST_INFO")?.get()
            ?.addOnSuccessListener { documentSnapshot ->
                val testData = documentSnapshot?.data
                testData?.let {
                    val pdfUrlKey = "TEST${testID}_PDF_URL"
                    val pdfUrl = testData[pdfUrlKey] as? String
                    pdfUrl?.let {
                        // If PDF URL is found, open and download the PDF
                        openPdf(pdfUrl, context)
                    }
                }
            }
            ?.addOnFailureListener {
                // Handle failure
                Toast.makeText(context, "Failed to retrieve PDF URL", Toast.LENGTH_SHORT).show()
            }
    }
    fun openPdf(pdfUrl: String, context: Context) {
        // Here, you can implement code to open and download the PDF using the provided URL
        // For example, you can use an Intent to open the PDF in a PDF viewer app
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(pdfUrl)
        context.startActivity(intent)
    }
    fun loadMyScores(completeListener: MyCompleteListener){
        g_firestore?.collection("USERS")?.document(FirebaseAuth.getInstance().uid!!)
            ?.collection("USER_DATA")?.document("MY_SCORES")
            ?.get()
            ?.addOnSuccessListener {documentSnapshot-> for(i in 0 until testList.size){
                var top:Int=0
                if(documentSnapshot.get(testList[i].testID)!=null){
                    top= documentSnapshot.getLong(testList[i].testID)?.toInt() ?:0
                }
                testList[i].topScore=top

            }
                completeListener.OnSucces()
            }
            ?.addOnFailureListener{completeListener.OnFailure()}
    }
    fun saveResult(score: Int,  completeListener: MyCompleteListener) {
        val batch = g_firestore?.batch()
        val userDoc = g_firestore?.collection("USERS")?.document(FirebaseAuth.getInstance().uid!!)

        if (score > testList[g_selected_test].topScore) {
            val scoreDoc = userDoc?.collection("USER_DATA")?.document("MY_SCORES")
            val testData = hashMapOf<String, Any>(
                testList[g_selected_test].testID to score
            )
            batch?.set(scoreDoc!!, testData, SetOptions.merge())
        }
        batch?.commit()
            ?.addOnSuccessListener(){
                if (score > testList[g_selected_test].topScore) {
                    testList[g_selected_test].topScore = score
                }
                completeListener.OnSucces()
            }
            ?.addOnFailureListener(){
                completeListener.OnFailure()
            }
        // Assuming the rest of your function implementation continues here
    }
    public fun saveAccountData(name: String,surname: String,completeListener: MyCompleteListener){
        val accountData = mutableMapOf<String, Any>()
        accountData["NAME"] = name
        accountData["SURNAME"] = surname
        g_firestore?.collection("USERS")?.document(FirebaseAuth.getInstance().uid!!)
            ?.update(accountData)
            ?.addOnSuccessListener {
                my_account.name=name
            my_account.surname=surname
            completeListener.OnSucces()}
            ?.addOnFailureListener{completeListener.OnFailure()}
    }

    fun loadQuestions(completeListener: MyCompleteListener){
        questionsList = ArrayList()
        questionsList.clear()
        g_firestore?.collection("Questions")
            ?.whereEqualTo("TEST", testList.get(g_selected_test).testID)
            ?.get()
            ?.addOnSuccessListener { queryDocumentSnapshots ->
                for (doc in queryDocumentSnapshots) {
                    questionsList.add(
                        QuestionsModel(
                            doc.getString("QUESTION").toString(),
                            doc.getString("A").toString(),
                            doc.getString("B").toString(),
                            doc.getString("C").toString(),
                            doc.getString("D").toString(),
                            doc.getLong("ANSWER")?.toInt() ?: 0,
                            -1
                        )
                    )
                }
                completeListener.OnSucces() // Notify listener that the operation is complete
            }
            ?.addOnFailureListener { exception ->
                // Handle failure
                completeListener.OnFailure()
            }



    }
}

