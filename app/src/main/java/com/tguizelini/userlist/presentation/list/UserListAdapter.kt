package com.tguizelini.userlist.presentation.list

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tguizelini.userlist.databinding.UserListItemBinding
import com.tguizelini.userlist.domain.model.User
import java.lang.Exception

class UserListAdapter(
    private val onItemClick: ((item: User) -> Unit)?
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private val items = ArrayList<User>()

    fun setData(newItems: List<User>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: UserListItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: User) {
            view.itemTvName.text = item.name

            item.avatar?.let {
                val img = try {
                    Uri.parse(it)
                } catch (err: Exception) {
                    null
                }

                view.itemIvAvatar.setImageURI(img)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount() = items.size
}