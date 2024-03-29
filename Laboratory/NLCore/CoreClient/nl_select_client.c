//
// Created by Caldremch on 2021/12/21.
//

#include "nl_select_client.h"
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <strings.h>
#include <nl-log.h>

log_handler g_nl_log_handler;


#define NL_PORT 7778
#define NL_IP "127.0.0.1"

int nl_select_client() {

    int conn_fd = 0;
    struct sockaddr_in client;
    char*server_ip_addr = NL_IP;

    client.sin_family = AF_INET;
    client.sin_port = htons(NL_PORT);
    client.sin_addr.s_addr = inet_addr(server_ip_addr);
    //socket函数介绍https://blog.csdn.net/xc_tsao/article/details/44123331
    conn_fd = socket(AF_INET, SOCK_STREAM, 0);

    if(conn_fd <0){
        nl_perror("socket error\n");
        return -2;
    }

    if(connect(conn_fd, (struct sockaddr*)&client, sizeof (client)) < 0){
        nl_perror("connect error:\n");
        return -3;
    }

    char buffer[1024];

    bzero(buffer, sizeof(buffer));

    recv(conn_fd, buffer, sizeof(buffer), 0);

    nl_printf("recv: %s\n", buffer);

    strcpy(buffer, "this is client\n");

    send(conn_fd, buffer, sizeof(buffer), 0);

//    while (1){
//        bzero(buffer, sizeof(buffer));
//        scanf("%s", buffer);
//        int l = strlen(buffer);
//        buffer[l] ='\0';
//        send(conn_fd, buffer, sizeof(buffer), 0);
//        nl_printf("i had sent buffer\n");
//    }

    close(conn_fd);
}

void nl_client_init(void (*log_handler)(const char *)) {
    g_nl_log_handler = log_handler;
}
