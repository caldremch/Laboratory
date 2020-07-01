package com.caldremch.laboratory.widget

import com.caldremch.widget.single.SelectItem

/**
 *
 * @author: Caldremch
 *
 * @date: 2020/6/30
 *
 * @describe:
 *
 **/

class HouseStructValue(var value: Int? = null, var isEditText: Boolean = false) : SelectItem() {

    companion object {
        const val ROOM = 1
        const val HALL = 2
        const val TOILET = 3
    }

}