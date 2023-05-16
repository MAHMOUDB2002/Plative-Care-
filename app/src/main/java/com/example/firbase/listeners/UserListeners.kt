package com.example.firbase.listeners

import com.example.firbase.model.User

interface UserListeners {
    fun onUserClicked(user: User?)
}