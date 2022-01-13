package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.util.Log
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.nativelaboratory.c.NativeLaboratory
import com.caldremch.nativelaboratory.c.RenderActionEngine

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/1/24 19:45
 *
 * @description
 *
 *
 */
@Entry
class NativeLaboratoryEntry : IEntry {
    override fun getTitle(): String {
        return "NativeLaboratoryEntry"
    }

    var w = 0;
    var h = 0;

    override fun onClick(context: Context) {
        if (RenderActionEngine.callback == null) {
            RenderActionEngine.callback = {
                Log.d("NativeLaboratoryEntry", "onClick: $it  ${it.w},${it.h}")
            }
        }
        NativeLaboratory.postRenderAction(w++, h++)
    }
}