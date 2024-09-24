package com.sandclan.search.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeDetails(
    @PrimaryKey(autoGenerate = false)
    val idMeal: String,
    val strArea: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strTags: String,
    val strYouTube: String,
    val strInstructions: String,
    val ingredientsPair: List<Pair<String, String>>
)
