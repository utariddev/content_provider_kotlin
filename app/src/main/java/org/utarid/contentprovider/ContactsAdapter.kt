package org.utarid.contentprovider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(_nameList: List<String>) : RecyclerView.Adapter<ContactsAdapter.ContatcsViewHolder>() {

    private var nameList: List<String> = _nameList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatcsViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.contacts_row, parent, false)
        return ContatcsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContatcsViewHolder, position: Int) {
        val contact: String = nameList[position]
        holder.tvName.text = contact
        holder.tvId.text = position.toString()
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    class ContatcsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvId: TextView = itemView.findViewById(R.id.tv_id)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
    }
}