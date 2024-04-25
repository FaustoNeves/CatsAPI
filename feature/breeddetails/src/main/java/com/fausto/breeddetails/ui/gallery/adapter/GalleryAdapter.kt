package com.fausto.breeddetails.base.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fausto.breeddetails.databinding.GalleryItemAdapterBinding
import com.fausto.designsystem.utils.GradientTransformation
import com.fausto.model.BreedImageModel
import com.squareup.picasso.Picasso

internal class GalleryAdapter(private val imagesList: List<BreedImageModel>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    internal inner class GalleryViewHolder(private val binding: GalleryItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            Picasso.get().load(url).transform(
                GradientTransformation()
            ).into(binding.galleryImage)
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