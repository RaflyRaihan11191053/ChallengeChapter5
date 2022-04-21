package com.example.challengechapter5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.challengechapter5.databinding.FragmentDetailItemBinding
import com.example.challengechapter5.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailItemFragment : Fragment() {

    lateinit var viewModel: DetailItemViewModel

    private var _binding: FragmentDetailItemBinding?= null
    private val binding get() = _binding!!

    private val args: DetailItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailItemFragment_to_homeFragment)
        }

        viewModel = ViewModelProvider(this).get(DetailItemViewModel::class.java)
        detailItem()
    }

    private fun detailItem() {
        val id = args.id
        viewModel.getDetail(id)
        viewModel.detail.observe(viewLifecycleOwner, Observer {
            detail ->
            Glide.with(binding.root).load(detail.image).into(binding.ivImage)
            binding.tvTitle.text = detail.title
            binding.tvCategory.text = detail.category
            binding.tvDescription.text = detail.description
            binding.tvPrice.text = detail.price.toString()
        })
    }


}