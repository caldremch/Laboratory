//
// Created by Caldremch on 2021/1/24.
//

#include "NativeLogger.h"
#include "MyLog.h"
#include <cstdlib>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <unistd.h>
#include <cstring>

/**
 * @param env
 * @param path
 * @param content
 */
void NativeLogger::save2File(JNIEnv *env, jstring path, jstring content) {
    __android_log_print(ANDROID_LOG_ERROR, "caldremch", "NativeLogger 打印....");
    //创建文件
    int len = env->GetStringLength(content);
    auto cPath = env->GetStringUTFChars(path, JNI_FALSE);
    //O_CREATE 若欲打开的文件不存在则自动建立该文件.
    //O_RDWR 读写方式
    int fd = open(cPath, O_RDWR | O_CREAT, 00777);
    //SEEK_END 将读写位置指向文件尾后再增加offset 个位移量,
    //相当于通过 lseek 来进行文件长度的扩展
    lseek(fd, len - 1, SEEK_END);
    //末尾空串代表结束符?
    write(fd, "", 1);
    //为什么先要执行上面两部? , 因为映射只能映射文件存在的大小, 说白了就是文件多大就是映射多大, 所以需要预先扩展好文件
    //的大小
    char *buffer = (char *) mmap(nullptr, len, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    //映射建立好, 关闭文件
    close(fd);
    //只要网 buffer 写入数据就可以
    memcpy(buffer, content, len);
    //解除映射
    munmap(buffer, len);
}