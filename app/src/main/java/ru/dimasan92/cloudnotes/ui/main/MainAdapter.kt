package ru.dimasan92.cloudnotes.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.data.model.Note

class MainAdapter : RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {

    var notes = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteTitle = itemView.findViewById<TextView>(R.id.title)
        private val noteBody = itemView.findViewById<TextView>(R.id.body)

        fun bind(note: Note) {
            with(note) {
                noteTitle.text = title
                noteBody.text = body
                itemView.setBackgroundColor(color)
            }
        }
    }
}