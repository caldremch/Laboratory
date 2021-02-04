package com.caldremch.laboratory.register

import android.content.Context
import com.tencent.matrix.plugin.DefaultPluginListener
import com.tencent.matrix.report.Issue
import com.tencent.matrix.util.MatrixLog


/**
 *
 * @author Caldremch
 *
 * @date 2021-02-04 14:12
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class TestPluginListener(context: Context) : DefaultPluginListener(context) {
    private val TAG = "TestPluginListener"
    override fun onReportIssue(issue: Issue) {
        super.onReportIssue(issue)
        MatrixLog.e(TAG, issue.toString())
        //add your code to process data
    }
}