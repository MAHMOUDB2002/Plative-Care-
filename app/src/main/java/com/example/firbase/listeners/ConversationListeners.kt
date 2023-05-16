package com.example.firbase.listeners

import com.example.firbase.model.User

interface ConversationListeners {
    fun onConversionClicked(user: User?)
}