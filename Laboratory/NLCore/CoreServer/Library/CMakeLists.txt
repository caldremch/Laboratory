cmake_minimum_required(VERSION 3.21)
project(CoreServer C)

set(CMAKE_C_STANDARD 99)

add_executable(CoreServer nl_select_server.c nl_select_server.h)
