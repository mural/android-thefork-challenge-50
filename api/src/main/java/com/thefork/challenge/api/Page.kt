package com.thefork.challenge.api

class Page<T>(
    val data: List<T>,
    val page: UInt,
    val total: UInt
)
