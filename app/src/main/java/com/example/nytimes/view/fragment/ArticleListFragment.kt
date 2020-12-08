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
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimes.R
import com.example.nytimes.adapter.ArticleAdapter
import com.example.nytimes.constant.AppConstant
import com.example.nytimes.databinding.FragmentArticleBinding
import com.example.nytimes.listener.ImplItemClick
import com.example.nytimes.model.Article
import com.example.nytimes.util.isInternetAvailable
import com.example.nytimes.view.activity.MainActivity
import com.example.nytimes.viewmodel.ArticleViewModel

class ArticleListFragment : Fragment(), ImplItemClick {
    private var mContext: Context? =null

    private var mFragmentArticleBinding: FragmentArticleBinding? = null
    lateinit var mViewModel: ArticleViewModel

    private var mArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFragmentArticleBinding= DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article,
            container,
            false
        )
        return mFragmentArticleBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)

        initializeRecyclerView()
        initializeObservers()

        //Check Internet Connection if true call server else call localDB
        if (isInternetAvailable(mContext)) {
            mViewModel.fetchArticleListFromServer()
        } else {
            AlertDialog.Builder(mContext).setMessage(R.string.no_connectivity_message).setPositiveButton("OK", {dialog, _ -> dialog.dismiss()}).show()
        }

        //Pull to Refresh
        mFragmentArticleBinding?.swipe?.setOnRefreshListener {
            mFragmentArticleBinding?.swipe?.isRefreshing = false
            if (isInternetAvailable(mContext)) {
                mViewModel.onRefreshClicked()
            } else {
                AlertDialog.Builder(mContext).setMessage(R.string.no_connectivity_message).setPositiveButton("OK", { dialog, _ -> dialog.dismiss()}).show()
            }
        }
    }

    private fun initializeRecyclerView() {
        mArticleAdapter = mContext?.let { ArticleAdapter(it, arrayListOf(), this) }
        mFragmentArticleBinding?.rvList?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mArticleAdapter
        }
    }

    private fun initializeObservers() {
        mViewModel.getFetchArticleLiveData().observe(activity as MainActivity, { kt ->
            (mArticleAdapter as ArticleAdapter).updateList(kt)
        })
        mViewModel.mShowApiError.observe(activity as MainActivity, {
            AlertDialog.Builder(mContext).setMessage(it).setPositiveButton("OK", {dialog, _ -> dialog.dismiss()}).show()
        })
        mViewModel.mShowProgressBar.observe(activity as MainActivity, { bt ->
            if (bt) {
                mFragmentArticleBinding?.progress?.visibility = View.VISIBLE
                mFragmentArticleBinding?.rvList?.visibility = View.GONE
                mFragmentArticleBinding?.swipe?.visibility = View.GONE
            } else {
                mFragmentArticleBinding?.progress?.visibility = View.GONE
                mFragmentArticleBinding?.rvList?.visibility = View.VISIBLE
                mFragmentArticleBinding?.swipe?.visibility = View.VISIBLE
            }
        })
    }

    override fun onItemClick(article: Article) {
        val fragmentTransaction: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        val articleDetailFragment = ArticleDetailFragment()

        val bundle = Bundle()
        bundle.putSerializable(AppConstant.ARTICLE_DATA, article)
        articleDetailFragment.setArguments(bundle)

        fragmentTransaction.addToBackStack(AppConstant.FRAGMENT_NAME_DETAIL)
        fragmentTransaction.replace(R.id.fl_fragment, articleDetailFragment, AppConstant.FRAGMENT_NAME_DETAIL)

        fragmentTransaction.commit()
    }
}