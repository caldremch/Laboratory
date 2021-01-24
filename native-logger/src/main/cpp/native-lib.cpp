#include <jni.h>
#include <string>
#include <stdio.h>
#include <android/log.h>
#include<errno.h>

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)

extern "C"
JNIEXPORT void JNICALL
Java_com_caldremch_android_log_NativeLogger_saveToFile(JNIEnv *env, jobject thiz, jstring path,
                                                       jstring content) {


}