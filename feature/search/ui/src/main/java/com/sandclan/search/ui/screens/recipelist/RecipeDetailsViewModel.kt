package com.sandclan.search.ui.screens.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandclan.common.utils.NetworkResult
import com.sandclan.common.utils.UIText
import com.sandclan.search.domain.usecases.GetRecipeDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetails.UIState())
    val uiState: StateFlow<RecipeDetails.UIState> get() = _uiState.asStateFlow()

    fun onEvent(event: RecipeDetails.Event) {
        when (event) {
            is RecipeDetails.Event.FetchRecipeDetails -> {
                recipeDetails(event.id)
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

}


object RecipeDetails {
    data class UIState(
        val isLoading: Boolean = false,
        val error: UIText = UIText.Idle,
        val data: com.sandclan.search.domain.model.RecipeDetails? = null
    )


    sealed interface Navigation {

    }

    sealed interface Event {
        data class FetchRecipeDetails(val id: String) : Event
    }
}