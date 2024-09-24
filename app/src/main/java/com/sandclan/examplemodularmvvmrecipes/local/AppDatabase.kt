package com.sandclan.examplemodularmvvmrecipes.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sandclan.search.data.local.RecipeDao
import com.sandclan.search.domain.model.Recipe
import com.sandclan.search.domain.model.RecipeDetails

@Database(entities = [Recipe::class,RecipeDetails::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "recipe_app_db")
                .fallbackToDestructiveMigration().build()
    }

    abstract fun recipeDao(): RecipeDao

}