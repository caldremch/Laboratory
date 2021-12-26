//
// Created by Caldremch on 2021/12/25.
//

#include "nl-log.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <errno.h>

char buffer[25];
void nl_printf(const char *format, ...) {
    char *out_message = NULL;
    memset(buffer, 0, 25);
    va_list args;
    va_start(args, format);
    int length = vsnprintf(buffer, sizeof(buffer), format, args);
    va_end(args);

    int buffer_size = sizeof(buffer);
    int final_size = 0;
    if (length < buffer_size) {
        final_size = buffer_size;
        out_message = (char *) malloc(sizeof(char) * final_size);
        memcpy(out_message, buffer, final_size);
    } else {
        final_size = length;
        out_message = (char *) malloc(sizeof(char) * length);
        va_start(args, format);
        vsnprintf(out_message, length + 1, format, args);
        va_end(args);
    }
    if (g_nl_log_handler) {
        g_nl_log_handler(out_message);
    } else {
        printf("%s", out_message);
    }
    free(out_message);
}

void nl_perror(const char *format, ...) {
    char *out_message = NULL;
    memset(buffer, 0, 25);
    va_list args;
    va_start(args, format);
    int length = vsnprintf(buffer, sizeof(buffer), format, args);
    va_end(args);

    int buffer_size = sizeof(buffer);
    int final_size = 0;
    if (length < buffer_size) {
        final_size = buffer_size;
        out_message = (char *) malloc(sizeof(char) * final_size);
        memcpy(out_message, buffer, final_size);
    } else {
        final_size = length;
        out_message = (char *) malloc(sizeof(char) * length);
        va_start(args, format);
        vsnprintf(out_message, length + 1, format, args);
        va_end(args);
    }
    if (g_nl_log_handler) {
        g_nl_log_handler(out_message);
        g_nl_log_handler(strerror(errno));
    } else {
        printf("%s", out_message);
        printf("\n%s", strerror(errno));
    }
    free(out_message);
}
