package com.caldremch.dialog.owner

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/
data class Contact(var name: String? = null, var phone: String? = null) : OwnerBaseData() {
    override fun toString(): String {
        return "Contact(name=$name, phone=$phone)"
    }
}