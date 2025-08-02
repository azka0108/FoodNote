package com.example.foodnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodnote.data.db.FoodDatabase
import com.example.foodnote.data.repo.FoodRepository
import com.example.foodnote.datastore.PreferenceManager
import com.example.foodnote.ui.screen.FormScreen
import com.example.foodnote.ui.screen.HomeScreen
import com.example.foodnote.ui.screen.LoginScreen
import com.example.foodnote.ui.screen.RegisterScreen
import com.example.foodnote.ui.viewmodel.FoodViewModelFactory
import com.example.foodnote.viewmodel.AuthViewModel
import com.example.foodnote.viewmodel.AuthViewModelFactory
import com.example.foodnote.viewmodel.FoodViewModel
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Init Database & Repository
        val database = FoodDatabase.getDatabase(applicationContext)
        val repository = FoodRepository(database.foodDao())

        // ✅ Init DataStore
        val pref = PreferenceManager(applicationContext)

        // ✅ Inject ViewModels dengan Factory
        val authViewModel: AuthViewModel by viewModels {
            AuthViewModelFactory(pref)
        }
        val foodViewModel: FoodViewModel by viewModels {
            FoodViewModelFactory(repository)
        }

        setContent {
            val navController = rememberNavController()

            // ✅ Observe StateFlow dari ViewModel
            val foodList by foodViewModel.foodList.collectAsState(initial = emptyList())

            NavHost(
                navController = navController,
                startDestination = if (authViewModel.isLoggedIn()) "home" else "login"
            ) {
                // ✅ LOGIN SCREEN
                composable("login") {
                    LoginScreen(
                        authViewModel = authViewModel,
                        onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onNavigateToRegister = {
                            navController.navigate("register")
                        }
                    )
                }

                // ✅ REGISTER SCREEN
                composable("register") {
                    RegisterScreen(
                        authViewModel = authViewModel,
                        onRegisterSuccess = {
                            navController.navigate("login") {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                    )
                }

                // ✅ HOME SCREEN
                composable("home") {
                    HomeScreen(
                        foodList = foodList,
                        onAddClick = { navController.navigate("form") },
                        onDeleteClick = { food -> foodViewModel.deleteFood(food) },

                        // ✅ Logout
                        onLogoutClick = {
                            authViewModel.logout()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        },

                        // ✅ Edit
                        onEditClick = { food ->
                            navController.navigate("edit/${food.id}")
                        }
                    )
                }

                // ✅ FORM SCREEN (Tambah Makanan Baru)
                composable("form") {
                    FormScreen(
                        onSaveClick = { food ->
                            foodViewModel.insertFood(food)
                            navController.popBackStack() // kembali ke Home
                        }
                    )
                }

                // ✅ EDIT SCREEN (Edit Data Makanan)
                composable(
                    route = "edit/{foodId}",
                    arguments = listOf(navArgument("foodId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val foodId = backStackEntry.arguments?.getInt("foodId") ?: 0
                    val foodToEdit = foodList.find { it.id == foodId }

                    FormScreen(
                        existingFood = foodToEdit,
                        onSaveClick = { updatedFood ->
                            foodViewModel.updateFood(updatedFood)
                            navController.popBackStack() // kembali ke Home setelah update
                        }
                    )
                }
            }
        }
    }
}
