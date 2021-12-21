//
// Created by Caldremch on 2021/12/21.
//

#include "nl_client.h"
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <arpa/inet.h>


int nl_client() {
    int conn_fd = 0;
    struct sockaddr_in client;
    char*server_ip_addr = "192.168.101.8";

    client.sin_family = AF_INET;
    client.sin_port = htons(1990);
    client.sin_addr.s_addr = inet_addr(server_ip_addr);
    conn_fd = socket(AF_INET, SOCK_STREAM, 0);

    if(conn_fd <0){
        perror("socket error\n");
        return -2;
    }

    if(connect(conn_fd, (struct sockaddr*)&client, sizeof (client)) < 0){
        perror("connect error:\n");
        return -3;
    }

    char buffer[1024];

    bzero(buffer, sizeof(buffer));

    recv(conn_fd, buffer, sizeof(buffer), 0);

    printf("recv: %s\n", buffer);

    strcpy(buffer, "this is client\n");

    send(conn_fd, buffer, sizeof(buffer), 0);

    while (1){
        bzero(buffer, sizeof(buffer));
        scanf("%s", buffer);
        int l = strlen(buffer);
        buffer[l] ='\0';
        send(conn_fd, buffer, sizeof(buffer), 0);
        printf("i had sent buffer\n");
    }

    close(conn_fd);
}
