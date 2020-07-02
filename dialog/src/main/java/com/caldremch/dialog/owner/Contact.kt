package com.caldremch.dialog.owner

/**
 *
 *Created by Caldremch on 2020/6/25.
 *
 **/
data class Contact(
    var name: String? = null,
    var phone: String? = null,
    var isFocus: Boolean = false
) :
    OwnerBaseData() {
    override fun toString(): String {
        return "Contact(name=$name, phone=$phone)"
    }

    fun copy(): Contact {
        val newContact = Contact(name, phone)
        newContact.maskPhone = maskPhone
        newContact.isEnable = isEnable
        return newContact
    }
}