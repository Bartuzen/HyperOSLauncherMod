package dev.bartuzen.hyperos_launcher_mod

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import me.zhanghai.compose.preference.getPreferenceFlow
import me.zhanghai.compose.preference.listPreference
import me.zhanghai.compose.preference.switchPreference

class SettingsActivity : ComponentActivity() {
    @SuppressLint("WorldReadableFiles")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Settings") }
                        )
                    }
                ) { innerPadding ->
                    val context = LocalContext.current
                    @Suppress("DEPRECATION")
                    ProvidePreferenceLocals(
                        flow = context.getSharedPreferences("preferences", Context.MODE_WORLD_READABLE).getPreferenceFlow()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            listPreference(
                                key = "icons_horizontal",
                                defaultValue = 4,
                                title = { Text(text = "Horizontal Icon Count") },
                                values = listOf(1, 2, 3, 4, 5, 6),
                                summary = { Text(text = it.toString()) }
                            )
                            listPreference(
                                key = "icons_vertical",
                                defaultValue = 6,
                                title = { Text(text = "Vertical Icon Count") },
                                values = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                                summary = { Text(text = it.toString()) }
                            )
                            switchPreference(
                                key = "disable_clear_all_killing_background_tasks",
                                title = { Text(text = "Disable Clear All Button Killing Background Tasks")},
                                defaultValue = false,
                            )
                        }
                    }
                }
            }
        }
    }
}
