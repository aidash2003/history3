package com.history.historyofkyrgyzstan

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.history.historyofkyrgyzstan.placeholder.PlaceholderContent.PlaceholderItem
import com.history.historyofkyrgyzstan.databinding.FragmentTestBinding
import com.history.historyofkyrgyzstan.databinding.ListItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class TestAdapter(

public var categories: ArrayList<my_class>

) : RecyclerView.Adapter<TestAdapter.myholder>() {
    lateinit var binding: ListItemBinding

    class myholder(i: View):RecyclerView.ViewHolder(i) {

         val binding= ListItemBinding.bind(i)


        fun setData(plant:my_class)= with(binding){
            testNo.text="Тест "+(plant.testNumber+1).toString()
            testName.text=plant.testName
            topScore.text=plant.topScore.toString()
            progressBar2.setProgress(plant.topScore)
            root.setOnClickListener{
                MyDb.g_selected_test=plant.testNumber.toInt()
                val intent=Intent(root.context,StartTestActivity::class.java)
                root.context.startActivity(intent)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return myholder(view)
    }

    override fun onBindViewHolder(holder: myholder, position: Int) {
        holder.setData(categories[position])
    }

    override fun getItemCount(): Int = categories.size}