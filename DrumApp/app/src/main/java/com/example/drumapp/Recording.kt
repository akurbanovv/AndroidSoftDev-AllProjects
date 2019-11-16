package com.example.drumapp

data class Recording(
    var id: String = "",
    var name: String = "",
    var date: String = "",
    var dateCreated: Long = 0L,
    var sequence: List<Int> = listOf(),
    var timestamps: List<Long> = listOf()
)