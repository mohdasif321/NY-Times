package com.example.nytimes.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nytimes.R
import com.example.nytimes.view.fragment.ArticleListFragment

class MainActivity : AppCompatActivity() {

    private val mFragmentManager = supportFragmentManager
    private val mArticleListfragment = ArticleListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Load Fragment in Activity
        val fragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_fragment, mArticleListfragment)
        fragmentTransaction.commit()
    }

    //Set Toolbar Title
    fun settoobarTitle(value: String) {
        supportActionBar!!.title = value
    }
}