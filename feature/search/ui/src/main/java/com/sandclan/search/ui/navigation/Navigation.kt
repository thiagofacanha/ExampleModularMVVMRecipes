package com.sandclan.search.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sandclan.common.navigation.FeatureApi
import com.sandclan.common.navigation.NavigationRoute
import com.sandclan.common.navigation.NavigationSubGraphRoute
import com.sandclan.search.ui.screens.recipedetails.RecipeDetailsScreen
import com.sandclan.search.ui.screens.recipelist.RecipeDetails
import com.sandclan.search.ui.screens.recipelist.RecipeDetailsViewModel
import com.sandclan.search.ui.screens.recipelist.RecipeList
import com.sandclan.search.ui.screens.recipelist.RecipeListScreen
import com.sandclan.search.ui.screens.recipelist.RecipeListViewModel

interface SearchFeatureApi : FeatureApi

class SearchFeatureApiImpl : SearchFeatureApi {
    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navHostController: androidx.navigation.NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.Search.route,
            startDestination = NavigationRoute.RecipeList.route
        ) {
            composable(NavigationRoute.RecipeList.route) {
                val viewModel = hiltViewModel<RecipeListViewModel>()
                RecipeListScreen(
                    viewModel = viewModel,
                    navHostController = navHostController
                ) { mealId ->
                    viewModel.onEvent(RecipeList.Event.GoToRecipeDetails(mealId))
                }
            }
            composable(NavigationRoute.RecipeDetails.route) {
                val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                val mealId = it.arguments?.getString("id")
                LaunchedEffect(key1 = mealId) {
                    mealId?.let { id ->
                        viewModel.onEvent(RecipeDetails.Event.FetchRecipeDetails(id))
                    }
                }

                RecipeDetailsScreen(viewModel = viewModel)
            }

        }
    }

}