package com.own_world.chatapp.Unit

class User {
    var name: String = ""
    var email: String = ""
    var uid: String = ""

    constructor(){}
    constructor(name: String, email: String, uid: String) {
        this.name = name
        this.email = email
        this.uid = uid
    }
}