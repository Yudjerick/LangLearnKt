package com.example.langlearnkt.data.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository {
    suspend fun registerUserWithEmail(email : String, password: String): Boolean{
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun loginUserWithEmail(email: String, password: String): Boolean{
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            true
        }catch (e: Exception){
            false
        }
    }
}