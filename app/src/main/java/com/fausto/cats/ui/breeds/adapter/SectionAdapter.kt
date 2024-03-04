package com.fausto.cats.ui.breeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fausto.cats.databinding.SectionItemAdapterBinding
import com.fausto.cats.domain.model.SectionModel

internal typealias SectionAdapterClickListener = ((breedId: String) -> Unit)

internal class SectionAdapter(
    private val sectionsList: List<SectionModel>, private val listener: SectionAdapterClickListener
) : RecyclerView.Adapter<SectionAdapter.ViewHolder>() {

    class ViewHolder(binding: SectionItemAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        var sectionName = binding.sectionName
        val breedsRecyclerView = binding.breedsRv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SectionItemAdapterBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = sectionsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sectionName.text = sectionsList[position].section
        holder.breedsRecyclerView.adapter =
            BreedsAdapter(sectionsList[position].breedsList, listener)
    }
}