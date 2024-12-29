package com.example.tests

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_matches")
data class SavedMatch(
    @PrimaryKey val id: String,
    val name: String,
    var isSaved: Boolean // Property to track if the match is saved
)
