package com.example.challegestori.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}