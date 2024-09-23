package com.sandclan.search.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sandclan.common.navigation.FeatureApi
import com.sandclan.common.navigation.NavigationRoute
import com.sandclan.common.navigation.NavigationSubGraphRoute
import com.sandclan.search.ui.screens.recipelist.RecipeListScreen
import com.sandclan.search.ui.screens.recipelist.RecipeListViewModel

interface SearchFeatureApi : FeatureApi

class SearchFeatureApiImpl : SearchFeatureApi {
    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navController: androidx.navigation.NavController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.Search.route,
            startDestination = NavigationRoute.RecipeList.route
        ) {
            composable(NavigationRoute.RecipeList.route) {
                val viewModel = hiltViewModel<RecipeListViewModel>()
                RecipeListScreen(viewModel = viewModel) {
                }
            }
            composable(NavigationRoute.RecipeDetails.route) {
            }

        }
    }

}