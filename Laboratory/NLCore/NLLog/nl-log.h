//
// Created by Caldremch on 2021/12/25.
//

#ifndef NLLOG_NL_LOG_H
#define NLLOG_NL_LOG_H
typedef void(*log_handler)(const char* fmt_c);
extern log_handler g_nl_log_handler;

void nl_printf(const char *format, ...);
void nl_perror(const char *format, ...);
#endif //NLLOG_NL_LOG_H
