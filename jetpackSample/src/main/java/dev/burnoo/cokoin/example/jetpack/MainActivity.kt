package dev.burnoo.cokoin.example.jetpack

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.burnoo.cokoin.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.random.Random

class MainViewModel : ViewModel() {

    val data = "Hello, DI!"
}

class NavViewModel : ViewModel() {

    val id = Random.nextInt()
}

private val appModule = module {
    viewModel { MainViewModel() }
    viewModel { NavViewModel() }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    Koin(appDeclaration = { modules(appModule) }) {
        Column {
            ViewModelSample()
            NavigationSample()
        }
    }
}

@Composable
fun ViewModelSample() {
    val viewModel = getViewModel<MainViewModel>()
    Text(viewModel.data)
}

@Composable
fun NavigationSample() {
    val navController = rememberNavController()
    KoinNavHost(navController, startDestination = "1") {
        composable("1") {
            Button(onClick = { navController.navigate("2") }) {
                val navViewModel = getNavViewModel<NavViewModel>()
                Text("Navigate to 2; viewModel id: ${navViewModel.id}")
            }
        }
        composable("2") {
            Button(onClick = { navController.navigate("1") }) {
                val navViewModel = getNavViewModel<NavViewModel>()
                Text("Navigate to 1; viewModel id: ${navViewModel.id}")
            }
        }
    }
}
