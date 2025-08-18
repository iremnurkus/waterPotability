package com.example.waterpotabilityapp.view
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.waterpotabilityapp.view.Ekranlar.Gecmis


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme() {
                MyBottomAppBarPreview()
            }
        }
    }
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}


@Preview
@Composable
fun MyBottomAppBarPreview(){
        MyBottomAppBar()
}

@Composable
fun MyBottomAppBar() {
    val isDarkTheme = isSystemInDarkTheme()

    val navController1 = rememberNavController()
    val selected = remember { mutableStateOf(Icons.Default.Add) }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(55.dp)){
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.DateRange
                        navController1.navigate(Ekranlar.Gecmis.screenName) {
                            popUpTo(0)
                        }
                    },

                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Home",
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.DateRange) Color.Black else Color.LightGray
                    )
                }
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Add
                        navController1.navigate(Ekranlar.YeniSorgu.screenName) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "YeniSorgu",
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.Add) Color.Black else Color.LightGray
                    )
                }
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.AccountCircle
                        navController1.navigate(Ekranlar.Kullanici.screenName) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "Kullanici",
                        modifier = Modifier.size(26.dp),
                         tint = if (selected.value == Icons.Default.AccountCircle) Color.Black else Color.LightGray
                    )
                }
            }
        }
    )
    { paddingValues ->
        NavHost(
            navController = navController1,
            startDestination = Ekranlar.YeniSorgu.screenName,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Ekranlar.Gecmis.screenName) { Gecmis() }
            composable(Ekranlar.YeniSorgu.screenName) { YeniSorgu(viewModel(), LocalContext.current) }
            composable(Ekranlar.Kullanici.screenName) { Kullanici(LocalContext.current) }
        }
    }
}

//@Preview
//@Composable
//fun AnaSayfa(context: Context?) {
//    Column(
//        modifier = Modifier.fillMaxSize()
//            .background(Color(0x00294D)),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Column(
//            modifier = Modifier.size(300.dp, 300.dp)
//                .clip(RoundedCornerShape(10.dp))
//                .background(Color.DarkGray),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            TextFieldEkle()
//            Spacer(modifier = Modifier.height(10.dp))
//            TextFieldEkle()
//            Spacer(modifier = Modifier.height(10.dp))
//            TextFieldEkle()
//            Spacer(modifier = Modifier.height(10.dp))
//            OutlinedButton(modifier = Modifier
//                .clip(RoundedCornerShape(10.dp)),
//                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
//                shape = RoundedCornerShape(10.dp),onClick = {
//                val tost = Toast.makeText(context, "Ya Bismillah", Toast.LENGTH_SHORT)
//                tost.show()
//            }) {
//                Text(text = "Hayde", color = Color.Black)
//            }
//        }
//    }
//}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TextFieldEkle() {
//    var inputFeatures by remember { mutableStateOf(("")) }
//    var predictionResult by remember { mutableStateOf<String>("") }
//    var isLoading by remember { mutableStateOf(false) }
//
//        OutlinedTextField(
//            value = inputFeatures,
//            onValueChange = { inputFeatures = it },
//            colors = TextFieldDefaults.textFieldColors(containerColor = Color.LightGray,
//                focusedIndicatorColor = Color.White,
//                unfocusedIndicatorColor = Color.Black),
//            modifier = Modifier.width(200.dp)
//            )
//}