package com.esgi4al.discooker.ui.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Comment

class CommentsAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userTextView: TextView = view.findViewById(R.id.comment_user_tv)
        val contentTextView: TextView = view.findViewById(R.id.comment_content_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.userTextView.text = comment.user.username
        holder.contentTextView.text = comment.content
    }

    override fun getItemCount() = comments.size
}