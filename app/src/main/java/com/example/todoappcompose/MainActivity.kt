package com.example.todoappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoappcompose.data.viewmodel.ShareViewModel
import com.example.todoappcompose.navigation.SetUpNavigation
import com.example.todoappcompose.ui.theme.ToDoAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val shareViewModel: ShareViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppComposeTheme {
                //entrypoint navigation
                navController = rememberNavController()
                SetUpNavigation(
                    navController = navController,
                    shareViewModel = shareViewModel
                )
            }
        }
    }
}