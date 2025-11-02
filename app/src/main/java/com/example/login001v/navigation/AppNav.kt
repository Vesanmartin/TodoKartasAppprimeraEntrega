package com.example.login001v.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.login001v.ui.home.MuestraDatosScreen
import com.example.login001v.ui.login.LoginScreen
import com.example.login001v.view.DrawerMenu
import com.example.login001v.view.ProductoFormScreen
import com.example.login001v.view.QrRoute

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // Login
        composable("login") {
            LoginScreen(navController = navController)
        }

        // MuestraDatosScreen con username
        composable(
            route = "muestraDatos/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            MuestraDatosScreen(username = username, navController = navController)
        }

        // DrawerMenu con username
        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            DrawerMenu(username = username, navController = navController)
        }

        // Detalle de producto (query params)
        composable(
            route = "ProductoFormScreen?nombre={nombre}&precio={precio}&imgRes={imgRes}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType; defaultValue = "" },
                navArgument("precio") { type = NavType.StringType; defaultValue = "" },
                // 👇 imgRes es un Int (resource id), no String
                navArgument("imgRes") { type = NavType.IntType; defaultValue = 0 }
            )
        ) { backStackEntry ->
            val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?: "")
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            // 👇 clave correcta "imgRes" y lectura como Int
            val imgRes = backStackEntry.arguments?.getInt("imgRes") ?: 0

            ProductoFormScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                imgRes = imgRes
            )
        }

        // QR
        composable("qrScanner") {
            QrRoute(
                navController = navController,
                returnKey = "qr"
            )
        }
    }
}// fin AppNav

