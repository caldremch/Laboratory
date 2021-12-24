package com.caldremch.laboratory.entry.entry

import android.content.Context
import android.util.Log
import com.caldremch.android.annotation.entry.Entry
import com.caldremch.android.annotation.entry.IEntry
import com.caldremch.laboratory.draft.RetryWithDelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okio.EOFException
import okio.IOException

/**
 *
 * @author Caldremch
 *
 * @date 2020-07-19 14:42
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@Entry
class RxJavaRetryEntry : IEntry {

    private val TAG = this.javaClass.simpleName

    override fun getTitle(): String {
        return "RxJavaRetryEntry"
    }


    override fun onClick(context: Context) {
        var count = 0
        Observable.create<String> {
            count++
            Log.d(TAG, "onClick: $count")
            if (count == 1 || count == 2) {
                val ioException = IOException(
                    "unexpected end of stream on Connection{",
                    EOFException("\\n not found: limit=0 content=…")
                )
                throw ioException
            } else if (count == 3 || count == 4) {
                throw NullPointerException("slsllsl")
            } else {
                it.onNext("well done!")
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen(RetryWithDelay(5, 0)).subscribe(object : Observer<String> {

                override fun onSubscribe(d: Disposable?) {
                    Log.d(TAG, "onSubscribe: ")
                }

                override fun onNext(t: String?) {
                    Log.d(TAG, "onNext: $t")
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ")
                    e?.printStackTrace()
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: ")
                }

            })
    }

}