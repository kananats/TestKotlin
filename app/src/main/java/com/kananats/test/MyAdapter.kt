package com.kananats.test

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerview1_item.view.*
import io.reactivex.subjects.BehaviorSubject


class MyAdapter(data: Array<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>()  {

    /// Data (rx)
    val strings = BehaviorSubject.create<Array<String>>()

    init {
        // Initial data
        this.strings.onNext(data)

        // Subscribe to changes
        this.strings.subscribe {
            it!!.forEachIndexed { index, _ -> this.notifyItemChanged(index) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview1_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = this.strings.value!!.size

    override fun onBindViewHolder(parent: MyAdapter.ViewHolder, position: Int) {
        Log.i("com.kananats", "onBindViewHolder() $position")

        parent.itemView.textView2.text = this.strings.value!![position]
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
