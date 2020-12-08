package com.example.nytimes.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nytimes.R
import com.example.nytimes.constant.AppConstant
import com.example.nytimes.databinding.FragmentArticleDetailBinding
import com.example.nytimes.model.Article
import com.example.nytimes.util.isInternetAvailable
import com.example.nytimes.view.activity.MainActivity
import com.example.nytimes.viewmodel.ArticleDetailViewModel

class ArticleDetailFragment: Fragment() {
    private var mContext: Context? =null

    private var mFragmentArticleDetailBinding: FragmentArticleDetailBinding? = null
    lateinit var mViewModel: ArticleDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFragmentArticleDetailBinding= DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article_detail, container, false
        )
        return mFragmentArticleDetailBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(ArticleDetailViewModel::class.java)

        initializeObservers()

        //Check Internet Connection if true call server else call localDB
        if (isInternetAvailable(mContext)) {
            val article: Article = arguments!!.getSerializable(AppConstant.ARTICLE_DATA) as Article
            mViewModel.fetchArticleDetailFromServer(article)
        } else {
            AlertDialog.Builder(mContext).setMessage(R.string.no_connectivity_message).setPositiveButton("OK", {dialog, _ -> dialog.dismiss()}).show()
        }
    }

    private fun initializeObservers() {
        mViewModel.getFetchArticleDetailLiveData().observe(
            activity as MainActivity, { kt ->
                mFragmentArticleDetailBinding?.articleDetailViewModel = kt
                mFragmentArticleDetailBinding?.date?.visibility = View.VISIBLE
            })
        mViewModel.mShowApiError.observe(activity as MainActivity, {
            AlertDialog.Builder(mContext).setMessage(it).setPositiveButton("OK",
                { dialog, _ -> dialog.dismiss()}).show()
        })
        mViewModel.mShowProgressBar.observe(activity as MainActivity, { bt ->
            if (bt) {
                mFragmentArticleDetailBinding?.progress?.visibility = View.VISIBLE
            } else {
                mFragmentArticleDetailBinding?.progress?.visibility = View.GONE
            }
        })
    }
}