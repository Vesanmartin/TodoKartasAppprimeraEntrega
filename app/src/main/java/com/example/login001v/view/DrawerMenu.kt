package com.example.login001v.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable

fun DrawerMenu(
    username:String,
    navController: NavController
){ // inicio Drawer
    Column(modifier=Modifier.fillMaxSize() )
    { // inicio columna

        Box(
            modifier=Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) // fin Box
        {// inicio contenido
            Text(
               text="Categorias user:$username",
                style=MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier=Modifier
                    .align(Alignment.BottomStart)
            )
        }// termino contenido

        // Items
        //LazyColumn: Creas la lista de elemnetos que se desplazan verticalmente

        LazyColumn (modifier=Modifier.weight(1f) )
        {
            item{ // item 1
                NavigationDrawerItem(
                    label = {Text("Hamburgesa Clasica")},
                    selected = false,
                    onClick = {
                        val nombre= Uri.encode("Hamburgesa Clasica")
                        val precio="5000"

                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    },// fin onclick

                    icon = {Icon(Icons.Default.Fastfood , contentDescription = "Clasica"    )}
                )// fin navigation

            } // fin item 1

            item{ // item 2
                NavigationDrawerItem(
                    label = {Text("Hamburgesa BBQ")},
                    selected = false,
                    onClick = {
                        /*  accion pendiente */
                    },// fin onclick

                    icon = {Icon(Icons.Default.LunchDining , contentDescription = "BBQ"    )}
                )// fin navigation

            } // fin item 2


            item{ // item 3
                NavigationDrawerItem(
                    label = {Text("Hamburgesa Veggie")},
                    selected = false,
                    onClick = {
                        /*  accion pendiente */
                    },// fin onclick

                    icon = {Icon(Icons.Default.Grass , contentDescription = "Veggie"    )}
                )// fin navigation

            } // fin item 3

            item{ // item 4
                NavigationDrawerItem(
                    label = {Text("Hamburgesa Picante")},
                    selected = false,
                    onClick = {
                        /*  accion pendiente */
                    },// fin onclick

                    icon = {Icon(Icons.Default.LocalFireDepartment , contentDescription = "Picante"    )}
                )// fin navigation

            } // fin item 4

            item{ // item 5
                NavigationDrawerItem(
                    label = {Text("Hamburgesa Doble")},
                    selected = false,
                    onClick = {
                        /*  accion pendiente */
                    },// fin onclick

                    icon = {Icon(Icons.Default.Star , contentDescription = "Doble"    )}
                )// fin navigation

            } // fin item 5


        }// fin Lazy


        //Footer
        Text(
            text="@ 2025 Burger App",
            style=MaterialTheme.typography.bodySmall,
            modifier=Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center


        )



    }// fin columna

}// fin DrawerMENU



@Preview(showBackground = true)
@Composable


fun DrawerMenuPreview(){
    val navController = androidx.navigation.compose.rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}