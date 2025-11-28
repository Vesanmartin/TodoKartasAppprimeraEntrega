package com.example.login001v.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.login001v.ui.home.MuestraDatosScreen
import com.example.login001v.ui.login.LoginScreen
import com.example.login001v.ui.login.RegistroScreen
import com.example.login001v.view.CarritoScreen
import com.example.login001v.view.CatalogoScreen
import com.example.login001v.view.DrawerMenu
import com.example.login001v.view.ProductoFormScreen
import com.example.login001v.view.QrRoute
import com.example.login001v.view.PostScreen
import com.example.login001v.viewmodel.CartViewModel
import com.example.login001v.viewmodel.PostViewModel

@Composable
fun AppNav() {
    val navController = rememberNavController()

    // 🔹 Un solo CartViewModel compartido en toda la app
    val cartViewModel: CartViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }

        // Ruta para crear cuenta
        composable("registro") {
            RegistroScreen(navController = navController)
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

            // 🔹 Aquí también usamos el mismo cartViewModel
            ProductoFormScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                imgRes = imgRes,
                cartViewModel = cartViewModel
            )
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

            // 🔹 AHORA sí pasamos el cartViewModel al catálogo
            CatalogoScreen(
                username = username,
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        // Ruta de búsqueda de cartas (PostScreen)
        composable("posts_list_route") {
            val postViewModel: PostViewModel = viewModel()
            PostScreen(viewModel = postViewModel)
        }

        // Ruta del carrito
        composable("cart_route") {
            CarritoScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
    }
} // fin AppNav
