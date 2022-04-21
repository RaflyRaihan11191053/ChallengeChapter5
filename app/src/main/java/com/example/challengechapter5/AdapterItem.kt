package com.example.challengechapter5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter5.databinding.ItemSellBinding

class AdapterItem(private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<AdapterItem.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSellBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetAllItemSell){
            binding.apply {
                tvTitle.text = data.title
                tvCategory.text = data.category
                tvPrice.text = data.price.toString()
                Glide.with(binding.root).load(data.image).into(ivItem)
                root.setOnClickListener {
                    onItemClick(data.id)
                }
            }
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<GetAllItemSell>() {
        override fun areItemsTheSame(
            oldItem: GetAllItemSell,
            newItem: GetAllItemSell
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: GetAllItemSell,
            newItem: GetAllItemSell
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun itemsData(value: List<GetAllItemSell>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemSellBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnClickListener {
        fun onClickItem(data: GetAllItemSell)
    }
}