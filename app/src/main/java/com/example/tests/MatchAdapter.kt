package com.example.tests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchAdapter(
    private val onStarClicked: (SavedMatch) -> Unit // Callback for star click
) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    private val matches = mutableListOf<SavedMatch>()

    fun setMatches(newMatches: List<SavedMatch>) {
        matches.clear()
        matches.addAll(newMatches)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.bind(match)
        holder.starIcon.setOnClickListener { onStarClicked(match) }
    }

    override fun getItemCount() = matches.size

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.text_name)
        val starIcon: ImageView = itemView.findViewById(R.id.icon_star)

        fun bind(match: SavedMatch) {
            nameText.text = match.name
            // Update the star icon based on whether the match is saved or not
        }
    }
}
