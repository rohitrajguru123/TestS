package com.example.tests

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_matches")
data class MatchEntity(
    @PrimaryKey val id: String,
    val name: String
)
