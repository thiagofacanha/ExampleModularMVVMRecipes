package com.sandclan.examplemodularmvvmrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sandclan.examplemodularmvvmrecipes.navigation.NavigationSubGraphs
import com.sandclan.examplemodularmvvmrecipes.navigation.RecipeNavigation
import com.sandclan.examplemodularmvvmrecipes.ui.theme.ExampleModularMVVMRecipesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationSubGraphs: NavigationSubGraphs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleModularMVVMRecipesTheme {
                Surface(modifier = Modifier.safeContentPadding()) {
                    RecipeNavigation(
                        modifier = Modifier.fillMaxSize(),
                        navigationSubGraphs = navigationSubGraphs
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExampleModularMVVMRecipesTheme {
        Greeting("Android")
    }
}