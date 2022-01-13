#include <jni.h>
#include <string.h>
#include <android/log.h>

#define TAG "Native-Laboratory"
#define log_v(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)

void jni_log(const char *fmt_c) {
    log_v("%s", fmt_c);
}


static jobject globalElement = NULL;
static jclass clz_RenderObject = NULL;
static jclass clz_RenderActionEngine = NULL;
static jmethodID jmethodId_onRender = NULL;

JNIEXPORT void JNICALL
Java_com_caldremch_nativelaboratory_c_NativeLaboratory_postRenderAction(JNIEnv *env, jobject thiz,
                                                                        jint w, jint h) {
    if ((*env)->IsSameObject(env, clz_RenderObject, NULL)) {
        jclass clz = (*env)->FindClass(env, "com/caldremch/nativelaboratory/c/RenderObject");
        clz_RenderObject = (*env)->NewGlobalRef(env, clz);
    }


    if (clz_RenderActionEngine == NULL) {
        jclass clz = (*env)->FindClass(env, "com/caldremch/nativelaboratory/c/RenderActionEngine");
        clz_RenderActionEngine = (*env)->NewGlobalRef(env, clz);
    }

//    if (jmethodId_onRender == NULL) {
        jmethodID jmethodId_onRender = (*env)->GetStaticMethodID(env, clz_RenderActionEngine,
                                                 "onRender",
                                                 "(Lcom/caldremch/nativelaboratory/c/RenderObject;)V");
//        jmethodId_onRender = (*env)->NewGlobalRef(env, me);
//    }

    if (globalElement == NULL) {
        jmethodID jmethodId_init = (*env)->GetMethodID(env, clz_RenderObject, "<init>", "(II)V");
        jobject element = (*env)->NewObject(env, clz_RenderObject, jmethodId_init, 0, 0);
        globalElement = (*env)->NewGlobalRef(env, element);
    }

    jfieldID native_w = (*env)->GetFieldID(env, clz_RenderObject, "w", "I");
    jfieldID native_h = (*env)->GetFieldID(env, clz_RenderObject, "h", "I");
    (*env)->SetIntField(env, globalElement, native_w, w);
    (*env)->SetIntField(env, globalElement, native_h, h);
    (*env)->CallStaticVoidMethod(env, clz_RenderActionEngine, jmethodId_onRender, globalElement);
}