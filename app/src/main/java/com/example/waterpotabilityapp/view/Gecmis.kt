package com.example.waterpotabilityapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.waterpotabilityapp.Post
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.security.Timestamp

//class Gecmis :Fragment() {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        VeriAl()
//    }
//}

fun VeriAl(callback: (ArrayList<Post>) -> Unit) {
    val postList: ArrayList<Post> = arrayListOf()
    val db = Firebase.firestore
    val user= FirebaseAuth.getInstance().currentUser
    db.collection("Kayıtlar")
        .get()
        .addOnSuccessListener { value ->
            if (!value.isEmpty) {
                val documents = value.documents
                for (document in documents) {
                    val sorgu = document.get("sorgu") as? String ?: continue
                    val sonuc = document.get("sonuc") as? String ?: continue
                    val tarih = document.get("tarih") as? com.google.firebase.Timestamp ?: continue
                    val email = document.get("email") as? String ?: continue

                    if (user?.email == email) {
                        val post = Post(email, tarih, sorgu, sonuc)
//                        println("${post.email} ${post.sorgu} ${post.sonuc} ${post.tarih}")
                        postList.add(post)
                    }else{
                        continue
                    }
                }
                callback(postList)
            }
        }
        .addOnFailureListener { error ->
            println(error.message)
        }
}


@Composable
fun Gecmis() {
    VeriAl { postList ->
        for (post in postList) {
            println("${post.email}, ${post.sorgu}, ${post.sonuc}, ${post.tarih}")
        }
    }

    val postList = remember { mutableStateOf<List<Post>>(emptyList()) }

    LaunchedEffect(Unit) {
        VeriAl { posts ->
            postList.value = posts
        }
    }

    PostListScreen(postList = postList.value)

//    var postList: ArrayList<Post> = VeriAl()
//    if(!postList.isEmpty){
//        postList[0].email
//        //    println(postList[0].email.toString()+" "+postList[0].sorgu.toString()+" "+postList[0].sonuc.toString()+" "+postList[0].tarih.toString())
////    Text(text = postList[0].email)
////    Text(text = postList[1].email)
////    Text(text = postList[2].email)
//    }else{
//        Toast.makeText(LocalContext.current,"Bombos ve bombok",Toast.LENGTH_SHORT).show()
//    }
}

@Preview
@Composable
fun PostListScreen(postList: List<Post>) {
    LazyColumn {
        itemsIndexed(postList) { index, post ->
            var tarih= post.tarih.toDate().toString().split(" ")
            OutlinedCard(modifier = Modifier.padding(15.dp)) {
                Text(
                    text =  "Sorgu: ${post.sorgu}\n" +
                            "Sonuç: ${post.sonuc}\n" +
                            "Tarih: ${tarih[2]} ${tarih[1]} ${tarih[5]}, ${tarih[3]}",
                    modifier = Modifier.padding(8.dp)
                )
                HorizontalDivider() // Liste elemanlarını ayırmak için çizgi
            }
        }
    }
}

