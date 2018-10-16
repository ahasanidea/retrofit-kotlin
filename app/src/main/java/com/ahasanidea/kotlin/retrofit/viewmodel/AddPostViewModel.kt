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

class AddPostViewModel : ViewModel() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var post: MutableLiveData<Post>
    fun addPost(postinput: Post): LiveData<Post> {
        if (!::post.isInitialized) {
            post = MutableLiveData()
            loadPost(postinput)
        }
        return post
    }

    fun loadPost(postinput: Post) {
        val retrofit = RetrofitClient.instance
        val requestInterface = retrofit.create(RequestInterface::class.java)

        val disposable = requestInterface.addData(postinput)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Post>() {
                override fun onComplete() {

                }

                override fun onNext(postoutput: Post) {
                    post.value = postoutput
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