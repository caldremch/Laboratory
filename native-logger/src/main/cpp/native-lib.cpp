#include <jni.h>
#include <string>
#include <cstdio>
#include <android/log.h>
#include<cerrno>
#include "NativeLogger.h"

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)

extern "C"
JNIEXPORT void JNICALL
Java_com_caldremch_android_log_NativeLogger_saveToFile(JNIEnv *env, jobject thiz, jstring path,
                                                       jstring content) {
    auto *logger = new NativeLogger();
    logger->save2File(env, path, content);
}