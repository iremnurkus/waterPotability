package com.example.waterpotabilityapp.view

sealed class Ekranlar (val screenName : String)
{
    data object Gecmis : Ekranlar("Gecmis")
    data object YeniSorgu : Ekranlar("YeniSorgu")
    data object Kullanici : Ekranlar("Kullanici")
}