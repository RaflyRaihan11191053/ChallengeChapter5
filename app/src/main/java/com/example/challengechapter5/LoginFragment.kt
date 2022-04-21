package com.example.challengechapter5

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.challengechapter5.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {

    private var myDB: UserDatabase?= null

    private var _binding: FragmentLoginBinding?= null
    private val binding get() = _binding!!

    val sharedPreferences = "sharedPreferences"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginScreen: SharedPreferences = requireActivity().getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
        myDB = UserDatabase.getInstance(requireContext())

        binding.btnLogin.setOnClickListener {
            if (binding.etUsernameLogin.text.isNullOrEmpty() && binding.etPasswordLogin.text.isNullOrEmpty()){
                Toast.makeText(context, "Masukkan Username dan Password kamu terlebih dahulu", Toast.LENGTH_LONG).show()
            } else if (binding.etUsernameLogin.text.isNullOrEmpty()){
                binding.etUsernameLogin.error = "Masukkan Username kamu yang telah terdaftar"
            } else if (binding.etPasswordLogin.text.isNullOrEmpty()){
                binding.etPasswordLogin.error = "Masukkan Password kamu"
            } else {
                GlobalScope.async {
                    val result = myDB?.userDao()?.login(binding.etUsernameLogin.text.toString(), binding.etPasswordLogin.text.toString())
                    runBlocking(Dispatchers.Main) {
                        if (result == false){
                            Toast.makeText(context, "Sign In gagal", Toast.LENGTH_SHORT).show()
                        } else {
                            val editor: SharedPreferences.Editor = loginScreen.edit()
                            editor.putString("username", binding.etUsernameLogin.text.toString())
                            editor.putString("password", binding.etPasswordLogin.text.toString())
                            editor.apply()
                            Toast.makeText(context, "Sign In Berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }
                }
            }
        }

        binding.tvGoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}