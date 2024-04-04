package com.own_world.chatapp.Model

class Massage {
    var massage: String? = null
    var senderId: String? = null

    constructor(){}
    constructor(massage: String, senderId: String){
        this.massage = massage
        this.senderId = senderId
    }
}