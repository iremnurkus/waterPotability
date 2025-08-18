package com.example.waterpotabilityapp.view

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.waterpotabilityapp.view.GirisActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class Giris : Fragment() {

    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }
}

@Preview
@Composable
fun GirisEkrani(context: Context?) {
    val navController = rememberNavController()
    var sonuc: Boolean = false
    lateinit var auth: FirebaseAuth
    auth = Firebase.auth
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var eposta by remember { mutableStateOf("") }
        var sifre by remember { mutableStateOf("") }
        Column(
            modifier = Modifier.size(width = 600.dp, height = 250.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Eposta")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = eposta, onValueChange = { eposta = it })

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Şifre")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = sifre, onValueChange = { sifre = it },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
//            var text by remember { mutableStateOf("") }
//            var isFocused by remember { mutableStateOf(false) } // Odak durumunu izlemek için
//            val focusRequester = remember { FocusRequester() }
//
//            TextField(
//                value = text,
//                onValueChange = { text = it },
//                modifier = Modifier
//                    .focusRequester(focusRequester)
//                    .onFocusChanged { focusState ->
//                        isFocused = focusState.isFocused // Odak durumu değişimini izler
//                    }
//                    .background(
//                        if (isFocused) Color(0xFFEEEEFF) else Color.White, // Odak durumuna göre arka plan rengi
//                        shape = RoundedCornerShape(8.dp)
//                    )
//                    .padding(4.dp),
//            )
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(onClick = {
                        GirisKontrol(auth, eposta, sifre,context)

//                    println("butona tiklandi ${sonuc}")
//                    if (sonuc==true){
//                        println("merhaba")
//                        Toast.makeText(context, "Bem-Vindo", Toast.LENGTH_LONG).show()
//                        if (context != null) {
//                        } else {
//                            println("Context null!")
//                        }
//                    }
                }) {
                    Text(text = "Giriş Yap")
                    KeyboardOptions(keyboardType = KeyboardType.Password)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    KayitOl(auth,eposta, sifre, context)
                }) {
                    Text(text = "Kayıt Ol")
                    KeyboardOptions(keyboardType = KeyboardType.Password)
                }
            }
        }
    }

}
fun GirisKontrol(auth: FirebaseAuth, eposta: String, sifre: String, context: Context?) {
//    var sonuc: Boolean=false
//
//    try {
//        if (eposta == "irem" && sifre == "1905") {
//            sonuc=true
//            KayitOl(auth,eposta, sifre, context)
//        }else{
//            sonuc=false
//        }
//    }catch(e: Exception) {
//        Toast.makeText(context, "Hatalı giriş", Toast.LENGTH_LONG).show()
//    }
//
//    return sonuc

    try {
            auth.signInWithEmailAndPassword(eposta, sifre)
                .addOnCompleteListener(GirisActivity()) { task ->
                        if (task.isSuccessful) {
                            if (auth.currentUser?.isEmailVerified == true) {

                                context?.startActivity(
                            Intent(context, MainActivity::class.java))
//                    Toast.makeText(context, "Giriş başarılı", Toast.LENGTH_LONG).show()

                    }else{
                                Toast.makeText(context,"Mail adresinizi doğruladıktan sonra deneyiniz. ",Toast.LENGTH_LONG).show()
                            }}

                }.addOnFailureListener(GirisActivity()) { e ->
                    Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).show()
                }
    } catch (e: Exception) {
        Toast.makeText(context, "Bilgileri eksiksiz giriniz. ", Toast.LENGTH_LONG).show()
    }
}

fun KayitOl(auth: FirebaseAuth,eposta: String, sifre: String,context: Context?) {
    try {
        auth.createUserWithEmailAndPassword(eposta, sifre)
            .addOnCompleteListener(GirisActivity()) { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser

                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
//                Log.d(TAG, "Email sent.")
                                Toast.makeText(context,"Mail adresinizi doğrulayınız. ", Toast.LENGTH_LONG).show()
                            }
                        }
                        .addOnFailureListener { exception ->
                            println(exception)
                        }

//                // Sign in success, update UI with the signed-in user's information
//                Log.d(TAG, "createUserWithEmail:success")
//                val user = auth.currentUser
//                updateUI(user)
                } else {
//                // If sign in fails, display a message to the user.
//                Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                Toast.makeText(
//                    baseContext,
//                    "Authentication failed.",
//                    Toast.LENGTH_SHORT,
//                ).show()
//                updateUI(null)

                }
            }.addOnFailureListener(GirisActivity()) { e ->
                Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
            }
    }catch (e:Exception){
        Toast.makeText(context,"Bilgileri eksiksiz giriniz. ",Toast.LENGTH_LONG).show()
    }

}

//@Composable
//fun LoginScreen(navController: NavHostController, eposta: String,sifre: String) {
//    try {
//        if (eposta == "irem" && sifre == "1905") {
//            // Eğer doğruysa, ana ekran (home) yönlendirmesini yapıyoruz
//            navController.navigate("home")
//        }
//    } catch (e: Exception) {
//        Toast.makeText(LocalContext.current, "Hatalı giriş", Toast.LENGTH_LONG).show()
//    }
//}