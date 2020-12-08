package com.example.nytimes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimes.R
import com.example.nytimes.databinding.ItemArticleBinding
import com.example.nytimes.listener.ImplItemClick
import com.example.nytimes.model.Article
import com.example.nytimes.util.getProgressDrawable
import com.example.nytimes.util.loadImage

class ArticleAdapter(
    context: Context,
    private val articleList: ArrayList<Article>,
    private val implItemClick: ImplItemClick
) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var mContext: Context = context

    //Get a list data an refresh the recyclerView
    fun updateList(newArticles: List<Article>) {
        articleList.clear()
        articleList.addAll(newArticles)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ItemArticleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_article,
            parent,
            false
        )
        return ArticleViewHolder(mContext, binding)
    }

    override fun getItemCount() = articleList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.loadView(articleList[position], implItemClick)

    }

    class ArticleViewHolder(var context: Context, var itemBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        //load data to view
        fun loadView(article: Article, implItemClick: ImplItemClick) {
            itemBinding.article = article

            itemBinding.next.setOnClickListener {
                implItemClick.onItemClick(article)
            }
            itemBinding.image.loadImage(article.imageUrl, getProgressDrawable(context))
        }
    }
}