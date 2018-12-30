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

class MainActivity : AppCompatActivity() {

    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        observable.subscribe { item -> this.textView1.text = item }
            .addTo(this.disposeBag)


        Observable.just(1, 2, 1, 2)
            .subscribe { item ->
                Log.i("com.kananats", "${item}")
            }
            .addTo(disposeBag)
        this.textView1.text = "kuy"

        Log.i("com.kananats", "test")
    }
}
