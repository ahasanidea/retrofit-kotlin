package com.ahasanidea.kotlin.retrofit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast
import com.ahasanidea.kotlin.retrofit.model.Post
import com.ahasanidea.kotlin.retrofit.network.RequestInterface
import com.ahasanidea.kotlin.retrofit.network.RetrofitClient
import com.ahasanidea.kotlin.retrofit.viewmodel.AddPostViewModel


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add.*


class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

    }
    fun addView(view: View){
        if (txtTitle.text.isEmpty() || txtTitle.text.isNullOrEmpty()) {
            showToast("Title is empty..!!")
            return
        }
        if (txtBody.text.isEmpty() || txtBody.text.isNullOrEmpty()) {
            showToast("Body is empty..!!")
            return
        }
        addPost(Post("1",txtTitle.text.toString(),txtBody.text.toString()))

        finish()
    }

    private fun addPost(post:Post) {
        val model= ViewModelProviders.of(this).get(AddPostViewModel::class.java)
        model.addPost(post).observe(this, Observer <Post>{
            showToast(it.toString())
        })
    }
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}
