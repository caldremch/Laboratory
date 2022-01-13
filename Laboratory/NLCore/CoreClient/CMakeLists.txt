cmake_minimum_required(VERSION 3.10.2)
project(CoreClient C)

set(CMAKE_C_STANDARD 99)


include_directories(${CMAKE_CURRENT_SOURCE_DIR}/../NLLog)
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/../NLLog build)

add_library(CoreClient nl_select_client.c nl_select_client.h nl_poll_client.c nl_poll_client.h)

target_link_libraries(CoreClient
        NLLog)
