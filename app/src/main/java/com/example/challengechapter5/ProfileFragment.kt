package com.example.challengechapter5

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.challengechapter5.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ProfileFragment : Fragment() {

    lateinit var viewModel: HomeViewModel

    private var myDB: UserDatabase?= null

    private var _binding: FragmentProfileBinding?= null
    private val binding get() = _binding!!

    val sharedPreferences = "sharedPreferences"

    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileScreen: SharedPreferences = requireActivity().getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.icBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.btnLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = profileScreen.edit()
            editor.clear()
            editor.apply()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        myDB = UserDatabase.getInstance(requireContext())

        binding.etUsernameEdit.setText(args.user.username)
        binding.etEmailEdit.setText(args.user.email)
        binding.etPasswordEdit.setText(args.user.password)

        binding.btnEdit.setOnClickListener {

            val objectUser = User(
                args.user.id,
                binding.etUsernameEdit.text.toString(),
                binding.etEmailEdit.text.toString(),
                binding.etPasswordEdit.text.toString()
            )
            GlobalScope.async {
                val result = myDB?.userDao()?.updateProfile(objectUser)
                runBlocking(Dispatchers.Main) {
                    if (result != 0) {
                        viewModel.getDataUser(objectUser)
                        val editor: SharedPreferences.Editor = profileScreen.edit()
                        editor.putString("username", binding.etUsernameEdit.text.toString())
                        editor.putString("email", binding.etEmailEdit.text.toString())
                        editor.putString("password", binding.etPasswordEdit.text.toString())
                        editor.apply()
                        Toast.makeText(requireContext(), "Sukses mengubah profil user", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), "Gagal mengubah profil user", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}