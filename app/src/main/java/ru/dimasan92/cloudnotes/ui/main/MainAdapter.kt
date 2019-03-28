package ru.dimasan92.cloudnotes.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_note.*
import ru.dimasan92.cloudnotes.R
import ru.dimasan92.cloudnotes.data.model.Color
import ru.dimasan92.cloudnotes.data.model.Note

class MainAdapter(private val onItemClickListener: (note: Note) -> Unit) :
    RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {

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

    inner class NoteViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

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

            title.text = note.title
            body.text = note.body
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, color))
            itemView.setOnClickListener { onItemClickListener(note) }
        }
    }
}