//
// Created by Caldremch on 2021/12/21.
//

#ifndef CORE_NL_CLIENT_H
#define CORE_NL_CLIENT_H

int nl_select_client();

void nl_client_init(void(*log_handler)(const char* fmt_c));

#endif //CORE_NL_CLIENT_H
