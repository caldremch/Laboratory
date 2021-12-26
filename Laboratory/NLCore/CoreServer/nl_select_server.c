//
// Created by Caldremch on 2021/12/20.
//
//https://github.com/Liu-YT/IO-Multiplexing

#include "nl_select_server.h"
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


int nl_select_server_start() {

    int serverfd, acceptfd;

    //关于sockaddr_in 和 sockaddr的区别
    //https://blog.csdn.net/dongyanxia1000/article/details/80683738
    struct sockaddr_in local_address;

    unsigned int nl_server_port, listener_sum;
    nl_server_port = 7778;
    listener_sum = 10;
    serverfd = socket(AF_INET, SOCK_STREAM, 0);
    if (serverfd == -1) {
        nl_perror("socket error");
        return -1;
    }

    nl_printf("socket success: %d\n", serverfd);

    local_address.sin_family = AF_INET;
    //https://baike.baidu.com/item/htons%28%29/10081963
    //将一个16位数从主机字节顺序转换成网络字节顺序。 简单地说,htons()就是将一个数的高低位互换
    local_address.sin_port = htons(nl_server_port);
    local_address.sin_addr.s_addr = htonl(INADDR_ANY);///IP地址设置成INADDR_ANY,让系统自动获取本机的IP地址
    bzero(&(local_address.sin_zero), 0);//sin_zero一般不使用, 用于对齐


    //绑定端口
    if (bind(serverfd, (struct sockaddr *) &local_address, sizeof(struct sockaddr)) == -1) {
        nl_perror("bind error");
        return -2;
    }

    nl_printf("bind ok\n");


    if (listen(serverfd, listener_sum) == -1) {
        nl_perror("listener_error:\n");
        return -3;
    }

    nl_printf("listen ok\n");

// 监控文件描述符集合(可以理解为一个列表, 用于存储fd)
    fd_set client_fd_set;


// 监控文件描述符中最大的文件号
    int max_sock;

    struct timeval tv;

    //存放活动的sockfd
    int client_sock_fd[5];

    bzero(client_sock_fd, sizeof(client_sock_fd));

    //用来记录链接数量
    int conn_count = 0;
    max_sock = serverfd;
    char buffer[1024];

    int ret = 0;

    while (1) {
        // 初始化文件描述符到集合, 将数组清理,每次循环都要清空集合，否则不能检测描述符变化
        FD_ZERO(&client_fd_set);

        // 加入服务器描述符 serverfd是socket执行后返回的文件描述符
        FD_SET(serverfd, &client_fd_set);

        //设置超时时间
        tv.tv_sec = 30;//30秒
        tv.tv_usec = 0;

        // 把活动的句柄加入到文件描述符中, 下面新建连接时会加入
        for (int i = 0; i < 5; ++i) {
            if (client_sock_fd[i] != 0) {
                FD_SET(client_sock_fd[i], &client_fd_set);
            }
        }

//https://baike.baidu.com/item/select/12504672 select
//max_sock为什么要+1, 第一个参数是指集合中所有文件描述符的范围, 即所有文件描述符的最大值加1，不能错！
        ret = select(max_sock + 1, &client_fd_set, NULL, NULL, &tv);

        nl_printf("文件描述符发生变化:%d\n", ret);

        if (ret < 0) {
            nl_perror("select error\n");
            break;
        } else if (ret == 0) {
            nl_printf("timeout \n");
            continue;
        }

        //轮询描述符
        for (int i = 0; i < conn_count; ++i) {

            /**
             * 判断描述符fd是否在给定的描述符集fdset中，
             * 通常配合select函数使用，由于select函数成功返回时会将未准备好的描述符位清零。
             * 通常我们使用FD_ISSET是为了检查在select函数返回后，某个描述符是否准备好，
             * 以便进行接下来的处理操作。当描述符fd在描述符集fdset中返回非零值，否则，返回零
             */
            if (FD_ISSET(client_sock_fd[i], &client_fd_set)) {//不在则返回0
                nl_printf("start recv from client[%d]\n", i);
                ret = recv(client_sock_fd[i], buffer, sizeof(buffer), 0);//最后一个参数一般设置为0
                if (ret <= 0) {
                    nl_printf("client[%d] close\n", i);
                    close(client_sock_fd[i]);
                    client_sock_fd[i] = 0;
                } else {
                    nl_printf("recv from client[%d] %s\n", i, buffer);
                }
            }

        }


        //FD_ISSET 判断serverfd socket是否可读, 可以读的话, 就发挥非0, 所以如果在上面读取完数据后, 这里就返回0
        int fd_isset = FD_ISSET(serverfd, &client_fd_set);
        if (fd_isset) { //判断描述符fd是否在给定的描述符集fdset中,当描述符fd在描述符集fdset中返回非零值，否则，返回零。
            struct sockaddr_in client_addr;
            size_t size = sizeof(struct sockaddr_in);
            int sock_client = accept(serverfd, (struct sockaddr *) (&client_addr), (unsigned int *) &size);
            if (sock_client < 0) {
                nl_perror("accept error\n");
                continue;
            }


            if (conn_count < 5) {
                client_sock_fd[conn_count++] = sock_client;
                bzero(buffer, 0);
                strcpy(buffer, "this is server! welcome\n");
                send(sock_client, buffer, sizeof(buffer), 0);
                nl_printf("\nnew connection client[%d] %s:%d\n", conn_count - 1, inet_ntoa(client_addr.sin_addr),
                       ntohs(client_addr.sin_port));
                bzero(buffer, sizeof(buffer));
                ret = recv(sock_client, buffer, sizeof(buffer), 0);
                if (ret < 0) {
                    nl_perror("recv error\n");
                    close(serverfd);
                    return -1;
                }

                nl_printf("recv: %s\n", buffer);
                if (sock_client > max_sock) {
                    max_sock = sock_client;
                }
            } else {
                nl_printf("max conn, quit!\n");
                break;
            }
        }
    }


    for (int i = 0; i < 5; ++i) {
        if (client_sock_fd[i] != 0) {
            close(client_sock_fd[i]);
        }
    }

    close(serverfd);
    return 0;
}

void nl_server_init(void (*log_handler)(const char *)) {
    g_nl_log_handler = log_handler;
}

/**
 *
*/