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

set(NLLOG ${CMAKE_CURRENT_SOURCE_DIR}/../NLCore/NLLog)
add_subdirectory(${NLLOG} NLLog)

target_link_libraries(
        native-lib
        NLLog
        ${log-lib} )

