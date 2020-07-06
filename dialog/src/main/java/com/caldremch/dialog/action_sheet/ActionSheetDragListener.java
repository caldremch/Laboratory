package com.caldremch.dialog.action_sheet;

import java.util.List;

/**
 * @author: Caldremch
 * @date: 2020/7/6
 * @describe:
 **/

public interface ActionSheetDragListener {
    void onDragEnd(int startPos, int endPos, List<ActionData> data);
}
