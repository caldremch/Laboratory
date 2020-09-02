package com.caldremch.laboratory.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.caldremch.laboratory.R
import com.caldremch.laboratory.search.GetDataTask
import com.caldremch.laboratory.search.InputTask
import kotlinx.android.synthetic.main.activity_search_task.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-01 10:44
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class SearchTaskActivity : AppCompatActivity() {

    private val TAG = "SearchTaskActivity"

    private val delayTask by lazy { InputTask(this) }
    private val getDataTask by lazy { GetDataTask(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_task)
        initEvent()
    }

    /**
     * 获取数据的任务
     */
    fun execGetDataTask() {
        btn.setText("数据获取中....")
        getDataTask.exec()
    }

    private fun initEvent() {
        et_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (delayTask.isRunning()) {
                    delayTask.cancel()
                }
                if (getDataTask.isRunning()) {
                    getDataTask.cancel()
                }
                delayTask.exec(s?.toString(), 500)
            }
        })
    }

    fun onGetDataSuccess() {
        btn.setText("数据获取完成!")
    }


}