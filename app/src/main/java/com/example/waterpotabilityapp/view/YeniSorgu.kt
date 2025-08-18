package com.example.waterpotabilityapp.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waterpotabilityapp.PredictionViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Preview
@Composable
fun YeniSorgu(viewModel: PredictionViewModel?, context: Context?){
    MyAppTheme { YeniSorguTasarim(viewModel, context) }
}


@Composable
fun YeniSorguTasarim(viewModel: PredictionViewModel?, context: Context?) {
    val auth = Firebase.auth

    data class Girdi(
        val ph: String = "",
        val hardness: String = "",
        val solids: String = "",
        val chloramines: String = "",
        val sulfate: String = "",
        val conductivity: String = "",
        val organicCarbon: String = "",
        val trihalmethanes: String = "",
        val turbidity: String = ""
    )

    var result by remember { mutableStateOf("") }
    var girdiler by remember { mutableStateOf(Girdi()) }

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Yapay Zeka Su İçilebilirlik Testi", modifier = Modifier.padding(16.dp))
//
//        TextField(
//            value = inputFeatures,
//            onValueChange = { inputFeatures = it },
//            label = { Text("Özellikleri virgülle ayırarak girin") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {
//            val features = inputFeatures.split(",").map { it.trim().toFloat() }
//            viewModel?.fetchPrediction(features) { prediction ->
//                result = prediction
//            }
//        }) {
//            Text("Tahmin Et")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(text = result, modifier = Modifier.padding(16.dp))
    Column() {
        var text by remember { mutableStateOf("") }
        var isFocused by remember { mutableStateOf(false) } // Odak durumunu izlemek için
        val focusRequester = remember { FocusRequester() }

        Spacer(modifier = Modifier.padding(30.dp))
        Column(
            modifier = Modifier
                .height(600.dp)
                .width(500.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {

                Card(
                    modifier = Modifier
                        .size(width = 120.dp, height = 100.dp)
                ) {
                    Text(
                        text = "pH", style = TextStyle(fontSize = 12.sp),
//                    color = Color.White,
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Left,
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused // Odak durumu değişimini izler
                        }
                        .background(
                            if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
                            shape = RoundedCornerShape(8.dp)
                        ),
                        value = girdiler.ph,
                        textStyle = TextStyle(fontSize = 20.sp),
                        onValueChange = {
                            girdiler = girdiler.copy(ph = it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Card(
                    modifier = Modifier
                        .size(width = 120.dp, height = 100.dp)
                ) {
                    Text(
                        text = "Hardness", style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize()
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                isFocused = focusState.isFocused // Odak durumu değişimini izler
                            }
                            .background(
                                if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
                                shape = RoundedCornerShape(8.dp)
                            ),
                        value = girdiler.hardness,
                        textStyle = TextStyle(fontSize = 20.sp),
                        onValueChange = {
                            girdiler = girdiler.copy(hardness = it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Card(
                    modifier = Modifier
                        .size(width = 120.dp, height = 100.dp)
                ) {
                    Text(
                        text = "Solids", style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused // Odak durumu değişimini izler
                        }
                        .background(
                            if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
                            shape = RoundedCornerShape(8.dp)
                        ),
                        value = girdiler.solids,
                        textStyle = TextStyle(fontSize = 20.sp),
                        onValueChange = {
                            girdiler = girdiler.copy(solids = it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Card(
                    modifier = Modifier
                        .size(width = 120.dp, height = 100.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Chloramines", style = TextStyle(fontSize = 12.sp),
                        textAlign = TextAlign.Left,
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused // Odak durumu değişimini izler
                        }
                        .background(
                            if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
                            shape = RoundedCornerShape(8.dp)
                        ),
                        value = girdiler.chloramines,
                        textStyle = TextStyle(fontSize = 20.sp),
                        onValueChange = {
                            girdiler = girdiler.copy(chloramines = it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Card(
                    modifier = Modifier
                        .size(width = 120.dp, height = 100.dp)
                ) {
                    Text(
                        text = "Sulfate", style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused // Odak durumu değişimini izler
                        }
                        .background(
                            if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
                            shape = RoundedCornerShape(8.dp)
                        ),
                        value = girdiler.sulfate,
                        textStyle = TextStyle(fontSize = 20.sp),
                        onValueChange = {
                            girdiler = girdiler.copy(sulfate = it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Card(
                    modifier = Modifier
                        .size(width = 120.dp, height = 100.dp)
                ) {
                    Text(
                        text = "Conductivity", style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused // Odak durumu değişimini izler
                        }
                        .background(
                            if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
                            shape = RoundedCornerShape(8.dp)
                        ),
                        value = girdiler.conductivity,
                        textStyle = TextStyle(fontSize = 20.sp),
                        onValueChange = {
                            girdiler = girdiler.copy(conductivity = it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Card(
                    modifier = Modifier
                        .size(width = 120.dp, height = 100.dp)
                ) {
                    Text(
                        text = "Organic Carbon", style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused // Odak durumu değişimini izler
                        }
                        .background(
                            if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
                            shape = RoundedCornerShape(8.dp)
                        ),
                        value = girdiler.organicCarbon,
                        textStyle = TextStyle(fontSize = 20.sp),
                        onValueChange = {
                            girdiler = girdiler.copy(organicCarbon = it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Card(
                    modifier = Modifier
                        .size(width = 120.dp, height = 100.dp)
                ) {
                    Text(
                        text = "Trihalmethanes", style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused // Odak durumu değişimini izler
                        }
                        .background(
                            if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
                            shape = RoundedCornerShape(8.dp)
                        ),
                        value = girdiler.trihalmethanes,
                        textStyle = TextStyle(fontSize = 20.sp),
                        onValueChange = {
                            girdiler = girdiler.copy(trihalmethanes = it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Card(
                    modifier = Modifier
                        .size(width = 120.dp, height = 100.dp)
                ) {
                    Text(
                        text = "Turbidity", style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier
                            .padding(8.dp),
//                    color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused // Odak durumu değişimini izler
                        }
                        .background(
                            if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
                            shape = RoundedCornerShape(8.dp)
                        ),
                        value = girdiler.turbidity,
                        textStyle = TextStyle(fontSize = 20.sp),
                        onValueChange = {
                            girdiler = girdiler.copy(turbidity = it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(onClick = {
                var girdi: String = ""
                try {
                    girdi = "${girdiler.ph.toFloat()} ${girdiler.hardness.toFloat()} ${girdiler.solids.toFloat()} " +
                            "${girdiler.chloramines.toFloat()} ${girdiler.sulfate.toFloat()} ${girdiler.conductivity.toFloat()} " +
                            "${girdiler.organicCarbon.toFloat()} ${girdiler.trihalmethanes.toFloat()} ${girdiler.turbidity.toFloat()}"

                    val features = girdi.split(" ").map { it.trim().toFloat() }
                    viewModel?.fetchPrediction(features) { prediction ->
                        result = prediction
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show()

                        if (result != "Hata: Sunucu bağlantısı başarısız.") {
                            VeriTabanı(auth, girdi.toString(), result.toString(), context)
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Hatalı giriş yaptınız", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Sorgula", textAlign = TextAlign.Center)
            }

//        Button(onClick = {
//            val features = inputFeatures.split(",").map { it.trim().toFloat() }
//            viewModel?.fetchPrediction(features) { prediction ->
//                result = prediction
//            }
//        }) {
//            Text("Tahmin Et")
//        }
        }
        Spacer(modifier = Modifier.padding(50.dp))
    }
}

fun VeriTabanı(auth: FirebaseAuth, sorguStr: String, sonuc: String, context: Context?) {
    val db = Firebase.firestore
    val sorgu= hashMapOf <String,Any> ()
        sorgu.put("email",auth.currentUser?.email.toString())
        sorgu.put("sorgu",sorguStr)
        sorgu.put("sonuc",sonuc)
        sorgu.put("tarih", Timestamp.now())


    db.collection("Kayıtlar")
        .add(sorgu)
        .addOnSuccessListener {
            Toast.makeText(context,"Sorgu kaydedildi. ",Toast.LENGTH_LONG).show()
        }.addOnFailureListener {exception ->
            Toast.makeText(context," ${exception} ",Toast.LENGTH_LONG).show()
        }

//    // Create a new user with a first and last name
//    val user = hashMapOf(
//        "email" to "Ada",
//        "tarih" to "27.12.2024",
//
//        )
//// Add a new document with a generated ID
//    db.collection("users")
//        .add(user)
//        .addOnSuccessListener { documentReference ->
////            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//        }
//        .addOnFailureListener { e ->
////            Log.w(TAG, "Error adding document", e)
//        }
}