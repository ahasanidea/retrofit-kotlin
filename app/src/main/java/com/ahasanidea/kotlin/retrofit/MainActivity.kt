package com.ahasanidea.kotlin.retrofit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders

import android.content.Intent

import android.os.Bundle

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


import android.widget.Toast
import com.ahasanidea.kotlin.retrofit.adapter.DataAdapter
import com.ahasanidea.kotlin.retrofit.model.Post

import com.ahasanidea.kotlin.retrofit.viewmodel.PostViewModel



import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), DataAdapter.Listener {
   private lateinit var postViewModel:PostViewModel
    private val TAG = MainActivity::class.java.simpleName

    private var mAndroidArrayList: ArrayList<Post>? = null

    private var mAdapter: DataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            startActivity(Intent(this,AddActivity::class.java))
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {

        recyclerView1.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView1.layoutManager = layoutManager

        postViewModel= ViewModelProviders.of(this).get(PostViewModel::class.java)
        postViewModel.getPosts().observe(this, Observer<List<Post>> {
            mAndroidArrayList = ArrayList(it)
            mAdapter = DataAdapter(mAndroidArrayList!!, this)

            recyclerView1.adapter = mAdapter
        })
    }

    override fun onItemClick(post: Post) {

        Toast.makeText(this, "${post.title} Clicked !", Toast.LENGTH_SHORT).show()
    }

    override fun onItemDeleteClick(post: Post) {
        //Toast.makeText(this,"Item is deleted!",Toast.LENGTH_SHORT)
        showAlertDelete(post)
    }
    private fun showAlertDelete(post: Post) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirm Delete...")
        alertDialog.setMessage("Are you sure you want delete this?")
        alertDialog.setIcon(android.R.drawable.ic_delete)
        alertDialog.setPositiveButton("YES") { _, _ ->
            //userRecyclerViewModel!!.deleteItem(u)
        }
        alertDialog.setNegativeButton("NO") { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.show()
    }

}
