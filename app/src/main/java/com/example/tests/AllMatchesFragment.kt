package com.example.tests

import MatchResponse
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast

class AllMatchesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_matches, container, false)
        recyclerView = view.findViewById(R.id.recycler_all_matches)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Define the callback to handle star click
        val onStarClicked: (SavedMatch) -> Unit = { match ->
            // Add or remove match from database based on the current status of the match
            val database = MatchDatabase.getDatabase(requireContext())
            val matchDao = database.matchDao()

            if (match.isSaved) {
                matchDao.delete(match) // Delete match from database
            } else {
                matchDao.insert(match) // Insert match into database
            }
            // Toggle the saved status of the match
            match.isSaved = !match.isSaved
            adapter.notifyDataSetChanged() // Refresh the adapter to update star status
        }

        // Create and set the adapter, passing the callback
        adapter = MatchAdapter(onStarClicked)
        recyclerView.adapter = adapter

        fetchMatches()
        return view
    }

    private fun fetchMatches() {
        val apiService = Retrofit.Builder()
            .baseUrl("https://api.foursquare.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        apiService.getMatches("40.7484,-73.9857", "NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ", "20180616")
            .enqueue(object : Callback<MatchResponse> {
                override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {
                    response.body()?.response?.venues?.let { venues ->
                        // Convert venues to SavedMatch format and set it to the adapter
                        val savedMatches = venues.map { Venue ->
                            SavedMatch(Venue.id, Venue.name, false)
                        }
                        adapter.setMatches(savedMatches)
                    }
                }

                override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error fetching matches", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
