package com.esgi4al.discooker.models

data class Category(val name: String, val imgUrl: String) {
    override fun toString(): String {
        return name
    }
}
