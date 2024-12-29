package com.example.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SavedMatchesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_matches, container, false)
        recyclerView = view.findViewById(R.id.recycler_saved_matches)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Define the callback to handle star click
        val onStarClicked: (SavedMatch) -> Unit = { match ->
            // Add or remove from database as needed (based on match status)
            val database = MatchDatabase.getDatabase(requireContext())
            val matchDao = database.matchDao()

            if (match.isSaved) {
                matchDao.delete(match)
            } else {
                matchDao.insert(match)
            }
            // Update star status (assuming isSaved is a boolean)
            match.isSaved = !match.isSaved
            adapter.notifyDataSetChanged() // Refresh adapter
        }

        // Pass the callback to the adapter
        adapter = MatchAdapter(onStarClicked)
        recyclerView.adapter = adapter

        loadSavedMatches() // Load saved matches from the database
        return view
    }

    private fun loadSavedMatches() {
        val database = MatchDatabase.getDatabase(requireContext())
        val savedMatches = database.matchDao().getAllMatches() // Assume this function exists
        adapter.setMatches(savedMatches)
    }
}
