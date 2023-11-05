package com.share.portal.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding

class FileAdapter(private val context: Context): RecyclerView.Adapter<FileViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder =
    FileViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context)))

  override fun getItemCount(): Int {

  }

  override fun onBindViewHolder(holder: FileViewHolder, position: Int) {

  }

  interface FileListener {
    fun onFileClicked()
  }
}