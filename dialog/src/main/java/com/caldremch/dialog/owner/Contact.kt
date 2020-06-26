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

    fun copy(): Contact {
        val newContact = Contact(name, phone)
        newContact.showTitle = showTitle
        newContact.isEnable = isEnable
        return newContact
    }
}