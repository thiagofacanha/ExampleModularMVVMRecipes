package com.sandclan.search.ui.screens.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandclan.common.utils.NetworkResult
import com.sandclan.common.utils.UIText
import com.sandclan.search.domain.model.Recipe
import com.sandclan.search.domain.usecases.DeleteRecipeUseCase
import com.sandclan.search.domain.usecases.GetRecipeDetailsUseCase
import com.sandclan.search.domain.usecases.InsertRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val insertRecipeUseCase: InsertRecipeUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetails.UIState())
    val uiState: StateFlow<RecipeDetails.UIState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<RecipeDetails.Navigation>()
    val navigation: Flow<RecipeDetails.Navigation> get() = _navigation.receiveAsFlow()


    fun onEvent(event: RecipeDetails.Event) {
        when (event) {
            is RecipeDetails.Event.FetchRecipeDetails -> {
                recipeDetails(event.id)
            }

            RecipeDetails.Event.GoToRecipeListScreen -> viewModelScope.launch {
                _navigation.send(RecipeDetails.Navigation.GoToRecipeListScreen)
            }

            is RecipeDetails.Event.DeleteRecipe -> {
                deleteRecipeUseCase.invoke(event.recipeDetails.toRecipe()).launchIn(viewModelScope)
            }

            is RecipeDetails.Event.InsertRecipe -> {
                insertRecipeUseCase.invoke(event.recipeDetails.toRecipe()).launchIn(viewModelScope)
            }
        }
    }


    private fun recipeDetails(id: String) = getRecipeDetailsUseCase.invoke(id).onEach { result ->
        when (result) {
            is NetworkResult.Error -> {
                _uiState.update { RecipeDetails.UIState(error = UIText.RemoteString(result.message.toString())) }
            }

            is NetworkResult.Loading -> {
                _uiState.update { RecipeDetails.UIState(isLoading = true) }

            }

            is NetworkResult.Success -> {
                _uiState.update { RecipeDetails.UIState(data = result.data) }

            }
        }
    }.launchIn(viewModelScope)

    fun com.sandclan.search.domain.model.RecipeDetails.toRecipe(): Recipe {
        return Recipe(
            idMeal,
            strArea,
            strMeal,
            strMealThumb,
            strCategory,
            strTags,
            strYouTube,
            strInstructions
        )
    }

}


object RecipeDetails {
    data class UIState(
        val isLoading: Boolean = false,
        val error: UIText = UIText.Idle,
        val data: com.sandclan.search.domain.model.RecipeDetails? = null
    )


    sealed interface Navigation {
        data object GoToRecipeListScreen : Navigation
    }

    sealed interface Event {
        data class FetchRecipeDetails(val id: String) : Event
        data class InsertRecipe(val recipeDetails: com.sandclan.search.domain.model.RecipeDetails) :
            Event

        data class DeleteRecipe(val recipeDetails: com.sandclan.search.domain.model.RecipeDetails) :
            Event

        data object GoToRecipeListScreen : Event
    }
}