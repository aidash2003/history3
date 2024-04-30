package com.history.historyofkyrgyzstan



import com.history.historyofkyrgyzstan.MyDb
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.history.historyofkyrgyzstan.databinding.FragmentAccountBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var logoutButton:Button
    lateinit var binding: FragmentAccountBinding
    private lateinit var nameString: String
    private lateinit var surnameString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAccountBinding.inflate(inflater, container, false)


        cantEdit()
        binding.save.setOnClickListener { if(validate()){
            saveData()
        }
        }
        binding.cansel.setOnClickListener {cantEdit()  }
        binding.editButton.setOnClickListener{
            canEdit()
        }
        binding.buttonn.setOnClickListener {
            val intent=Intent(requireContext(),FileActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }
    private fun cantEdit(){
        binding.email.isEnabled=false
        binding.Name.isEnabled=false
        binding.surname.isEnabled=false
        binding.saveCancel.visibility=View.GONE

        binding.email.setText(MyDb.my_account.email)
        binding.Name.setText(MyDb.my_account.name)
        binding.surname.setText(MyDb.my_account.surname)
    }
    private fun canEdit(){
        binding.Name.isEnabled=true
        binding.surname.isEnabled=true
        binding.saveCancel.visibility=View.VISIBLE
    }
    private fun validate():Boolean{
        nameString=binding.Name.text.toString()
        surnameString=binding.surname.text.toString()
        if (nameString.isEmpty()){
            binding.Name.setError("name can not be empty")
            return false
        }
        if (surnameString.isEmpty()){
            binding.surname.setError("name can not be empty")
            return false
        }
        return true
    }
    private fun saveData(){ MyDb.saveAccountData(nameString,surnameString,object :MyCompleteListener{
    override fun OnSucces() {
        Toast.makeText(requireContext(),"its okey wrong",Toast.LENGTH_SHORT).show()
        cantEdit()
    }

    override fun OnFailure() {
        Toast.makeText(requireContext(),"something wrong",Toast.LENGTH_SHORT).show()
        cantEdit()
    }
})
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}