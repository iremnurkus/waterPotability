package com.example.waterpotabilityapp

import com.google.firebase.Timestamp

data class Post (
    var email: String,
    var tarih: Timestamp,
    var sorgu: String,
    var sonuc: String
)