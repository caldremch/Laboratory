#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <nl_select_server.h>
#include <nl_select_client.h>
#define TAG "Native-Laboratory"
#define log_v(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)

void jni_log(const char *fmt_c) {
    log_v("%s", fmt_c);
}


JNIEXPORT void JNICALL
Java_com_caldremch_nativelaboratory_c_NativeLaboratory_startSelectServer(JNIEnv *env,jobject thiz) {
    jni_log("开启服务器...");
    nl_server_init(jni_log);
    nl_select_server_start();
}

JNIEXPORT void JNICALL
Java_com_caldremch_nativelaboratory_c_NativeLaboratory_startPollServer(JNIEnv *env, jobject thiz) {

}

JNIEXPORT void JNICALL
Java_com_caldremch_nativelaboratory_c_NativeLaboratory_startEpollServer(JNIEnv *env, jobject thiz) {

}

JNIEXPORT void JNICALL
Java_com_caldremch_nativelaboratory_c_NativeLaboratory_startSelectClient(JNIEnv *env,
                                                                         jobject thiz) {
    jni_log("开启客户端...");
    nl_client_init(jni_log);
    nl_select_client();
}

JNIEXPORT void JNICALL
Java_com_caldremch_nativelaboratory_c_NativeLaboratory_startPollClient(JNIEnv *env, jobject thiz) {
}

JNIEXPORT void JNICALL
Java_com_caldremch_nativelaboratory_c_NativeLaboratory_startEpollClient(JNIEnv *env, jobject thiz) {
}