package com.example.chuxipetcare.ui.adapter

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chuxipetcare.R
import com.example.chuxipetcare.data.model.ChatMessage

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    private var messages = listOf<ChatMessage>()

    fun submitList(newList: List<ChatMessage>) {
        messages = newList
        notifyDataSetChanged()
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView.findViewById(R.id.chatLayout)
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val msg = messages[position]

        if (msg.isUser) { // Người dùng: Bên phải, màu xanh
            holder.layout.gravity = Gravity.END
            holder.tvMessage.setBackgroundColor(Color.parseColor("#DCF8C6"))
        } else { // AI: Bên trái, màu xám
            holder.layout.gravity = Gravity.START
            holder.tvMessage.setBackgroundColor(Color.parseColor("#EAEAEA"))
        }
        holder.tvMessage.text = msg.text
    }

    override fun getItemCount() = messages.size
}