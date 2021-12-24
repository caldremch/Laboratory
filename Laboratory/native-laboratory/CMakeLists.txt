cmake_minimum_required(VERSION 3.10.2)
set(CMAKE_C_STANDARD 99)
project("native-laboratory")
add_library(
             native-lib
             SHARED
        src/main/cpp/native-lib.c
        src/main/cpp/nl_server.c

)

find_library(
              log-lib

              log )


target_link_libraries(
        native-lib
        ${log-lib} )

