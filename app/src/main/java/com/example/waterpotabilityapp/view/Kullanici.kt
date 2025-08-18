package com.example.waterpotabilityapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.waterpotabilityapp.view.GirisActivity
import com.example.waterpotabilityapp.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Preview
@Composable
fun Kullanici(context:Context?) {
        EkranTasarim(context)
}

@SuppressLint("ResourceType")
@Composable
fun EkranTasarim(context: Context?) {

    var bilgiler: Array<String>
    bilgiler=BilgiGetir(context)

    var sifre by remember{ mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(80.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center) {
//            Image(
//                painter = painterResource(id = R.drawable.sihim),
//                contentDescription = "Sihim",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(150.dp)
//                    .clip(CircleShape)
//                    .border(6.dp, Color.White, CircleShape)
//            )
//    }
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Text("E-Posta", color = Color.White)

            var eposta by remember{ mutableStateOf("${bilgiler[1]}") }
            TextField(modifier = Modifier.fillMaxWidth(), value = eposta, onValueChange = {eposta=it})
            Spacer(modifier = Modifier.padding(10.dp))

            Text("Yeni Şifre:  ", color = Color.White)
            TextField(modifier = Modifier.fillMaxWidth(), value = sifre, onValueChange = {sifre=it})
            Spacer(modifier = Modifier.padding(10.dp))

        }
        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Button(modifier = Modifier.width(150.dp), onClick = {
                try{
                    BilgiGuncelle(sifre,context)
                    Toast.makeText(context, "Bilgiler Güncellendi!", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Toast.makeText(context,"Hata: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }

            }) {
                Text("Güncelle")
            }

            Spacer(modifier = Modifier.padding(35.dp))

            Button(onClick = { CikisYap(context)}) {
                Text("Çıkış Yap")
            }
        }


    }
}

fun BilgiGetir(context:Context?): Array<String>{
    var bilgiler =emptyArray<String>()
    val user = Firebase.auth.currentUser
    user?.let {
        for (profile in it.providerData) {
            // Id of the provider (ex: google.com)
            val providerId = profile.providerId

            // UID specific to the provider
            val uid = profile.uid

            // Name, email address, and profile photo Url
            val name = profile.displayName.toString()
            val email = profile.email.toString()
            val photoUrl = profile.photoUrl.toString()

            bilgiler=arrayOf(name, email, photoUrl)
        }
    }
    return bilgiler
}

fun CikisYap(context: Context?) {
    Firebase.auth.signOut()
    context?.startActivity(Intent(context, GirisActivity::class.java))
}

fun BilgiGuncelle(yeniSifre: String,context: Context?) {
    val user = Firebase.auth.currentUser

    user!!.updatePassword(yeniSifre)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println( "Password updated.")
            }
        }
}