cmake_minimum_required(VERSION 3.10.2)
project(CoreServer C)

set(CMAKE_C_STANDARD 99)

include_directories(${CMAKE_CURRENT_SOURCE_DIR}/../NLLog)
add_subdirectory(${CMAKE_CURRENT_SOURCE_DIR}/../NLLog build)

add_library(CoreServer nl_select_server.c nl_select_server.h nl_poll_server.c nl_poll_server.h)
target_link_libraries(CoreServer NLLog)
