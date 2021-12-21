cmake_minimum_required(VERSION 3.21)
project(Core C)

set(CMAKE_C_STANDARD 99)

add_executable(Core main.c nl_server.c nl_server.h)
