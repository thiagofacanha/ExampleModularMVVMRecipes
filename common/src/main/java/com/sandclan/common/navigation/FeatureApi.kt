package com.sandclan.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureApi {
    fun registerGraph(navGraphBuilder: NavGraphBuilder,
                      navController: NavController)
}