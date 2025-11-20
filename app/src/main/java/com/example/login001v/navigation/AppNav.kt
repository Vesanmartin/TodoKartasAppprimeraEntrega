package com.example.login001v.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.login001v.data.model.Post
import com.example.login001v.ui.home.MuestraDatosScreen
import com.example.login001v.ui.login.LoginScreen
import com.example.login001v.view.CatalogoScreen
import com.example.login001v.view.DrawerMenu
import com.example.login001v.view.PostScreen
import com.example.login001v.view.ProductoFormScreen
import com.example.login001v.view.QrRoute
import com.example.login001v.viewmodel.PostViewModel

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }

        composable(
            route = "muestraDatos/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            MuestraDatosScreen(username = username, navController = navController)
        }

        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            DrawerMenu(username = username, navController = navController)
        }

        composable(
            route = "ProductoFormScreen?nombre={nombre}&precio={precio}&imgRes={imgRes}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType; defaultValue = "" },
                navArgument("precio") { type = NavType.StringType; defaultValue = "" },
                navArgument("imgRes") { type = NavType.IntType; defaultValue = 0 }
            )
        ) { backStackEntry ->
            val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?: "")
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            val imgRes = backStackEntry.arguments?.getInt("imgRes") ?: 0
            ProductoFormScreen(navController, nombre, precio, imgRes)
        }

        composable("qrScanner") {
            QrRoute(navController = navController, returnKey = "qr")
        }

        composable(
            route = "catalogo/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            CatalogoScreen(username = username, navController = navController)
        }

        // COMPOSABLE PARA INTEGRAR PostScreen
       // *** RUTA DE BÚSQUEDA DE CARTAS (ACTUALIZADA) ***
        composable("posts_list_route") {
                // Instancia el nuevo ViewModel
                val postViewModel: PostViewModel = viewModel()

                // Muestra la nueva pantalla
                PostScreen(viewModel = postViewModel)
            }

    }
}