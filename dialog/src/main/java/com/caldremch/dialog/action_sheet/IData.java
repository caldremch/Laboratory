package com.caldremch.dialog.action_sheet;

/**
 * @author: Caldremch
 * @date: 2020/7/9
 * @describe: 不同的ActionSheet共用一个ActionData的时候,
 * <p>
 * {@link BaseActionSheetDialog#getData()} 返回的数据继承{@link IData}
 * 对于不同的点击事件传递的数据进行区分
 **/

public interface IData {
    int sheetType();
}
