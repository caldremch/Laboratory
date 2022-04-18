package com.caldremch.floatingwindow

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool

/**
 *
 * @author Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2022/4/12 16:32
 *
 * @description
 *
//第二个参数leftVolume为左侧音量值（范围= 0.0到1.0）
//第三个参数rightVolume为右的音量值（范围= 0.0到1.0）
//第四个参数priority 为流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理
//第五个参数loop 为音频重复播放次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次
//第六个参数 rate为播放的速率，范围0.5-2.0(0.5为一半速率，1.0为正常速率，2.0为两倍速率)
 *
 *
 */
internal object SoundManager {

    private var soundPool: SoundPool? = null
    private val map = hashMapOf<Int, Int>()

    fun play(type: Int, soundRes: Int) {
        if (soundPool == null) {
            val sp = createSoundInstance() ?: return
            sp.setOnLoadCompleteListener { _, sampleId, _ -> //第一个参数soundID
                if (map[type] == null) {
                    map[type] = sampleId
                }
                sp.play(sampleId, 1f, 1f, 1, 0, 1f)
            }
            soundPool = sp
        }

        //加载对应的音频
        if (map[type] == null) {
            soundPool!!.load(Utils.context, soundRes, 1)
        }else{
            soundPool!!.play(map[type]!!, 1f, 1f, 1, 0, 1f)
        }

    }

    private fun createSoundInstance(): SoundPool? {
        val builder = SoundPool.Builder()
        builder.setMaxStreams(2)
        val attrBuilder: AudioAttributes.Builder = AudioAttributes.Builder() //转换音频格式
        attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC)
        builder.setAudioAttributes(attrBuilder.build())
        return builder.build()
    }
}