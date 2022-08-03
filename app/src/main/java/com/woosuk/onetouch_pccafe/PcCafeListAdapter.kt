package com.woosuk.onetouch_pccafe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.Streams.parse
import com.woosuk.onetouch_pccafe.databinding.ItemPccafelistBinding
import android.net.Uri
import android.util.Log

class PcCafeListAdapter(private val pair: Pair<String,String>,private val context: Context) :
    ListAdapter<Place, PcCafeListAdapter.ViewHolder>(
        diffUtil
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPccafelistBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_pccafelist,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    //getItemCount() 오버라이딩 메서드 사라짐
    //override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)) //변경된 점 -> getItem(position) 메서드가 생겼다.
    }

    inner class ViewHolder(
        private val binding: ItemPccafelistBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val pair = this@PcCafeListAdapter.pair

        init {
            binding.root.setOnClickListener {

                val longtitude=binding.data?.x
                val latitude=binding.data?.y
                val currentLongtitude=pair.first
                val currentLatitude=pair.second
                Log.d("longtitude",longtitude.toString())
                Log.d("latitude",latitude.toString())
                Log.d("currentLongtitude",currentLongtitude.toString())
                Log.d("currentLatitude",currentLatitude.toString())
                val uri="kakaomap://route?sp=${currentLatitude},${currentLongtitude}&ep=${latitude.toString()},${longtitude.toString()}&by=FOOT"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                context.startActivity(intent)

            }
        }

        fun bind(item: Place?) {
            binding.data = item
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Place>() {

            override fun areItemsTheSame(oldItem: Place, newItem: Place) =
                oldItem.place_name == newItem.place_name

            override fun areContentsTheSame(oldItem: Place, newItem: Place) =
                oldItem == newItem

        }
    }
}