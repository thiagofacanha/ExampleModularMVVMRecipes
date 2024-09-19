package com.sandclan.search.domain.repository

import com.sandclan.search.domain.model.Recipe
import com.sandclan.search.domain.model.RecipeDetails

interface SearchRepository {

    suspend fun getRecipes(s: String): Result<List<Recipe>>

    suspend fun getRecipeDetails(id: String): Result<RecipeDetails>
}