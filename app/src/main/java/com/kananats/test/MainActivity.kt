package com.kananats.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hide keyboard when lose focus
        this.editText1.onFocusChangeListener = View.OnFocusChangeListener { view, isFocused ->
            if (!isFocused) { this.dismissKeyboard(view) }
        }

        // observe changes from EditText
        val observable = Observable.create<String> { emitter ->
            val textWatcher = object : TextWatcher {

                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    s?.toString()?.let { emitter.onNext(it) }
                }
            }

            this.editText1.addTextChangedListener(textWatcher)

            emitter.setCancellable { this.editText1.removeTextChangedListener(textWatcher) }
        }

        // forward changes to TextView
        observable.subscribe { item -> this.textView1.text = item }
            .addTo(this.disposeBag)

        // initialize RecyclerView

        this.viewManager = LinearLayoutManager(this)
        this.viewAdapter = MyAdapter(arrayOf("1", "2", "3"))

        this.recyclerView1.apply {
            setHasFixedSize(true)
            adapter = viewAdapter
            layoutManager = viewManager
        }

        // this.recyclerView1.scrollChangeEvents()
    }

    private fun dismissKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
