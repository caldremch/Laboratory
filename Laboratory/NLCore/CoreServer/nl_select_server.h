//
// Created by Caldremch on 2021/12/20
//

#ifndef CORE_NL_SERVER_H
#define CORE_NL_SERVER_H

int nl_select_server_start();

void nl_server_init(void(*log_handler)(const char* fmt_c));

#endif //CORE_NL_SERVER_H
