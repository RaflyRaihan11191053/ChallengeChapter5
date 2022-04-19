package com.example.challengechapter5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.challengechapter5.databinding.FragmentRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class RegisterFragment : Fragment() {

    private var myDB: UserDatabase?= null

    private var _binding: FragmentRegisterBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDB = UserDatabase.getInstance(requireContext())

        binding.btnRegister.setOnClickListener {
            if (binding.etUsernameRegister.text.isNullOrEmpty() &&
                binding.etEmailRegister.text.isNullOrEmpty() &&
                binding.etPasswordRegister.text.isNullOrEmpty() &&
                binding.etConfirmPassword.text.isNullOrEmpty()) {
                Toast.makeText(context, "Form regitrasi belum terisi, harap isi terlebih dahulu", Toast.LENGTH_LONG).show()
            } else if (binding.etUsernameRegister.text.isNullOrEmpty()) {
                binding.etUsernameRegister.error = "Tentukan Username kamu terlebih dahulu"
            } else if (binding.etEmailRegister.text.isNullOrEmpty()) {
                binding.etEmailRegister.error = "Masukkan Email yang akan kamu pakai"
            } else if (binding.etPasswordRegister.text.isNullOrEmpty()) {
                binding.etPasswordRegister.error = "Tentukan Password kamu terlebih dahulu"
            } else if (binding.etConfirmPassword.text.isNullOrEmpty()) {
                binding.etConfirmPassword.error = "Konfirmasi Password kamu"
            } else {
                GlobalScope.async {
                    val result = myDB?.userDao()?.register(
                        User(null,
                            binding.etUsernameRegister.text.toString(),
                            binding.etEmailRegister.text.toString(),
                            binding.etPasswordRegister.text.toString())
                    )
                    runBlocking(Dispatchers.Main) {
                        if (result != 0.toLong()){
                            Toast.makeText(activity, "Berhasil Sign Up", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        } else {
                            Toast.makeText(activity, "Gagal Sign Up", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding.icBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.tvGoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}