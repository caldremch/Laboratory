//
// Created by Caldremch on 2021/1/24.
//



#ifndef LABORATORY_NATIVELOGGER_H
#define LABORATORY_NATIVELOGGER_H

#include <jni.h>


class NativeLogger {
public:
    void save2File(JNIEnv *env, jstring path, jstring content);
};


#endif //LABORATORY_NATIVELOGGER_H
