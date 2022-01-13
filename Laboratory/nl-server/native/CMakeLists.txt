cmake_minimum_required(VERSION 3.10.2)
set(CMAKE_C_STANDARD 99)
project("native-laboratory")
add_library(
             native-lib

             SHARED

             src/main/cpp/native-lib.c
)

find_library(
              log-lib

              log )

set(NLSERVER ${CMAKE_CURRENT_SOURCE_DIR}/../../NLCore/CoreServer)
include_directories(${NLCLIENT} ${NLSERVER})
add_subdirectory(${NLSERVER} CoreServer)
message(打印测试 ${CMAKE_CURRENT_SOURCE_DIR})
message($CLIENT_DIR)


target_link_libraries(
        native-lib
        CoreServer
        ${log-lib} )

