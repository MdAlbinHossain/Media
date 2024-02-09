package bd.com.albin.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import bd.com.albin.media.ui.MediaApp
import bd.com.albin.media.ui.theme.MediaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediaTheme {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text(text = stringResource(id = R.string.app_name)) },
                            scrollBehavior = scrollBehavior
                        )
                    }) {
                    MediaApp(modifier = Modifier.padding(it))
                }
            }
        }
    }
}
