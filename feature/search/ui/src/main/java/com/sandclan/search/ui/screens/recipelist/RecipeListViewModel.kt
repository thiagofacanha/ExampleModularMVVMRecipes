package com.sandclan.search.ui.screens.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandclan.common.utils.NetworkResult
import com.sandclan.common.utils.UIText
import com.sandclan.search.domain.model.Recipe
import com.sandclan.search.domain.usecases.GetAllRecipeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val getAllRecipeUseCase: GetAllRecipeUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RecipeList.UIState())
    val uiState: StateFlow<RecipeList.UIState> get() = _uiState.asStateFlow()


    fun onEvent(event: RecipeList.Event) {
        when (event) {
            is RecipeList.Event.SearchRecipe -> {
                search(event.query)
            }
        }
    }


    private fun search(query: String) = viewModelScope.launch {
        getAllRecipeUseCase.invoke(query).onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
//                    _uiState.value = _uiState.value.copy(isLoading = true)
                    _uiState.update { RecipeList.UIState(isLoading = true) }

                }

                is NetworkResult.Error -> {
                    _uiState.update { RecipeList.UIState(error = UIText.RemoteString(result.message.toString())) }

                }

                is NetworkResult.Success -> {
                    _uiState.update { RecipeList.UIState(data = result.data) }

                }
            }

        }.launchIn(viewModelScope)
    }

}


object RecipeList {

    data class UIState(
        val isLoading: Boolean = false,
        val error: UIText = UIText.Idle,
        val data: List<Recipe>? = null
    )

    sealed interface Navigation {
        data class OpenRecipeDetails(val id: String) : Navigation
    }

    sealed interface Event {
        data class SearchRecipe(val query: String) : Event

    }
}