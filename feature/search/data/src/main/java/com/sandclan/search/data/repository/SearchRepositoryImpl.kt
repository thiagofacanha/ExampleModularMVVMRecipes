package com.sandclan.search.data.repository

import com.sandclan.search.data.local.RecipeDao
import com.sandclan.search.data.mappers.toDomain
import com.sandclan.search.data.remote.SearchApiService
import com.sandclan.search.domain.model.Recipe
import com.sandclan.search.domain.model.RecipeDetails
import com.sandclan.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val searchApiService: SearchApiService,
    private val recipeDao: RecipeDao
) : SearchRepository {

    override suspend fun getRecipes(s: String): Result<List<Recipe>> {
        return try {
            val response = searchApiService.getRecipes(s)
            if (response.isSuccessful) {
                Result.success(response.body()?.meals?.toDomain() ?: emptyList())
            } else {
                Result.failure(Exception("Error getting recipes"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
        return try {
            val response = searchApiService.getRecipeDetails(id)
            if (response.isSuccessful) {
                response.body()?.meals?.let {
                    if (it.isNotEmpty()) {
                        Result.success(it.first().toDomain())
                    } else {
                        Result.failure(Exception("Error getting recipe with id: $id"))
                    }
                } ?: run {
                    Result.failure(Exception("Error getting recipe with id: $id"))
                }
            } else {
                Result.failure(Exception("Error getting recipe with id: $id"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    override suspend fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }
}