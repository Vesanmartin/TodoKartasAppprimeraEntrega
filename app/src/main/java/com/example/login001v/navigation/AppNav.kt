package com.example.login001v.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.login001v.ui.home.MuestraDatosScreen
import com.example.login001v.ui.login.LoginScreen

@Composable

fun AppNav(){
    // Crear navContoller para gestionar la navegacion

    val navController = rememberNavController()

    NavHost(navController=navController, startDestination = "login"){

        composable("login"){
            LoginScreen(navController = navController)
        }// fin composable 1

       composable(
           route="muestraDatos/{username}",
           arguments = listOf(
               navArgument("username"){
                   type = NavType.StringType
               }
           )//fin listof

       ) // fin composable 2
       {// inicio back
           backStackEntry->
           val username = backStackEntry.arguments?.getString("username").orEmpty()
           MuestraDatosScreen(username=username, navController = navController)

       }


    }// fin NavHost
}// fin AppNav

