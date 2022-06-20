package com.thefork.challenge.domain

data class UserLocation(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val timezone: String
)