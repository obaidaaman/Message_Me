package com.example.messageme.Activities.model

class Message {
     var message : String? = null
    var senderId : String? = null
    var receiverId: String? = null

    constructor(){}

    constructor(message: String?, senderId : String?){
        this.message= message
        this.senderId= senderId
    }




}