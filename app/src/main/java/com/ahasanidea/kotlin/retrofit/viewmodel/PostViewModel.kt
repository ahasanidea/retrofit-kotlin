package com.ahasanidea.kotlin.retrofit.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ahasanidea.kotlin.retrofit.model.Post
import com.ahasanidea.kotlin.retrofit.network.RequestInterface
import com.ahasanidea.kotlin.retrofit.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PostViewModel : ViewModel() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var posts: MutableLiveData<List<Post>>
    fun getPosts(): LiveData<List<Post>> {
        if (!::posts.isInitialized) {
            posts = MutableLiveData()
            loadPosts()
        }
        return posts
    }

    private fun loadPosts() {
        val retrofit = RetrofitClient.instance
        val requestInterface = retrofit.create(RequestInterface::class.java)

        val disposable=requestInterface.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableObserver<List<Post>>(){
                override fun onComplete() {

                }

                override fun onNext(postList: List<Post>) {
                    posts.value=postList
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            })
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}