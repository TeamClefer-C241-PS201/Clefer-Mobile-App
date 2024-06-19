package com.jimbonlemu.clefer.views.dashboard.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.clefer.databinding.ItemDashboardSliderBinding
import com.jimbonlemu.clefer.source.local.Slider


@SuppressLint("NotifyDataSetChanged")
class SliderAdapter : RecyclerView.Adapter<SliderAdapter.viewHolder>() {
    private var data = ArrayList<Slider>()
    inner class viewHolder( private var itemBinding: ItemDashboardSliderBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: Slider, position: Int){
            itemBinding.apply {
                imageView.setImageResource(item.image)
            }
        }
    }

    fun addItems(items: List<Slider>){
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(ItemDashboardSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(data[position], position)
    }
}

