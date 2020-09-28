package com.caldremch.dialog.action_sheet;

import java.util.List;

/**
 * @author: Caldremch
 * @date: 2020/7/6
 * @describe: 拖拽监听
 **/

public interface ActionSheetDragListener {

    /**
     * 拖拽回调
     *
     * @param startPos 拖拽开始的位置
     * @param endPos   拖拽结束的位置
     * @param data     拖拽结束后的列表
     * @param row      哪一行拖拽
     */
    void onDragEnd(int startPos, int endPos, List<BaseActionData> data, int row);
}
