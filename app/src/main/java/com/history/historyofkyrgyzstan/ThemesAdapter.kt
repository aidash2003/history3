package com.history.historyofkyrgyzstan

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.history.historyofkyrgyzstan.placeholder.PlaceholderContent.PlaceholderItem
import com.history.historyofkyrgyzstan.databinding.FragmentThemesBinding
import com.history.historyofkyrgyzstan.databinding.ListItem2Binding


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ThemesAdapter(

    public var categorie: ArrayList<my_class>

) : RecyclerView.Adapter<ThemesAdapter.Newholder>() {
    lateinit var binding: ListItem2Binding

    class Newholder(i: View):RecyclerView.ViewHolder(i) {

        val binding= ListItem2Binding.bind(i)


        fun setData(plant: my_class) = with(binding) {
            testNo.text = "Тест ${(plant.testNumber + 1).toString()}"
            testName.text = plant.testName

            // Handle item click
            root.setOnClickListener {
                // Call the method to open and download PDF file
                MyDb.openAndDownloadPdf(plant.testNumber+1, root.context)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Newholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item_2,parent,false)
        return Newholder(view)
    }

    override fun onBindViewHolder(holder: Newholder, position: Int) {
        holder.setData(categorie[position])
    }

    override fun getItemCount(): Int = categorie.size}
