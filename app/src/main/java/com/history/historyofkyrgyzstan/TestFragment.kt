package com.history.historyofkyrgyzstan

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.history.historyofkyrgyzstan.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class TestFragment : Fragment() {

    private lateinit var adapter2: TestAdapter

    private var columnCount = 1
    //private lateinit var testList: ArrayList<my_class>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test_list, container, false)

        // Set up RecyclerView
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter2 = TestAdapter(ArrayList()) // Pass an empty list for now
                adapter = adapter2
                // Load data into adapter
                MyDb.loadUserData(object : MyCompleteListener {
                    override fun OnSucces() {
                        MyDb.loadMyScores(object :MyCompleteListener{
                            override fun OnSucces() {
                                adapter2.categories = MyDb.testList // Update data in adapter
                                adapter2.notifyDataSetChanged()
                            }

                            override fun OnFailure() {
                                Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_LONG).show()
                            }
                        })
                        // Notify adapter of data change
                    }

                    override fun OnFailure() {
                        Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_LONG).show()
                    }
                })
                // Initialize adapter2 after loading data from Firestore
                // Set adapter to RecyclerView// Pass an empty list for now
                 // Set adapter to RecyclerView
            }
        }
        return view
    }

    companion object {
        // Constants
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) = TestFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columnCount)
            }
        }
    }
}