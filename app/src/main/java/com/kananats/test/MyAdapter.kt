package com.kananats.test

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recyclerview1_item.view.*

class MyAdapter(private val strings: Array<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>()  {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview1_item, parent, false)

        return ViewHolder(view)
}

override fun getItemCount() = this.strings.size

override fun onBindViewHolder(parent: MyAdapter.ViewHolder, position: Int) {
    parent.itemView.textView2.text = this.strings[position]
}
}
