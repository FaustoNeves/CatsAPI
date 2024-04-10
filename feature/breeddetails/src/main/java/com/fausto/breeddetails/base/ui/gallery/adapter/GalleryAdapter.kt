package com.fausto.breeddetails.base.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.fausto.breeddetails.databinding.GalleryItemAdapterBinding
import com.fausto.model.BreedImageModel

internal class GalleryAdapter(private val imagesList: List<BreedImageModel>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    internal inner class GalleryViewHolder(private val binding: GalleryItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            binding.galleryImage.setImageURI(url.toUri())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryItemAdapterBinding.inflate(LayoutInflater.from(parent.context)).run {
            GalleryViewHolder(this)
        }
    }

    override fun getItemCount() = imagesList.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(imagesList[position].url)
    }
}