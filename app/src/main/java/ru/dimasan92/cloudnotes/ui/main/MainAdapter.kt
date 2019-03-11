package ru.dimasan92.cloudnotes.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.data.model.Color
import ru.dimasan92.cloudnotes.data.model.Note

class MainAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {

    interface OnItemClickListener {
        operator fun invoke(note: Note)
    }

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

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteTitle = itemView.findViewById<TextView>(R.id.title)
        private val noteBody = itemView.findViewById<TextView>(R.id.body)

        fun bind(note: Note) {
            val color = when (note.color) {
                Color.WHITE -> R.color.color_white
                Color.VIOLET -> R.color.color_violet
                Color.YELLOW -> R.color.color_yellow
                Color.RED -> R.color.color_red
                Color.PINK -> R.color.color_pink
                Color.GREEN -> R.color.color_green
                Color.BLUE -> R.color.color_blue
            }

            with(note) {
                noteTitle.text = title
                noteBody.text = body
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, color))
            }
            itemView.setOnClickListener { onItemClickListener(note) }
        }
    }
}