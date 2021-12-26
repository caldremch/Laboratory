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

set(NLCLIENT ${CMAKE_CURRENT_SOURCE_DIR}/../NLCore/CoreClient)
set(NLSERVER ${CMAKE_CURRENT_SOURCE_DIR}/../NLCore/CoreServer)
#set(NLLOG ${CMAKE_CURRENT_SOURCE_DIR}/../NLCore/NLLog)
include_directories(${NLCLIENT} ${NLSERVER})
add_subdirectory(${NLCLIENT}  CoreClient)
add_subdirectory(${NLSERVER} CoreServer)
#add_subdirectory(${NLLOG} NLLog)

message(打印测试 ${CMAKE_CURRENT_SOURCE_DIR})
message($CLIENT_DIR)

#add_library($CLIENT_DIR)

#add_subdirectory($client_dir)
#add_subdirectory($server_dir)




target_link_libraries(
        native-lib
        CoreClient
        CoreServer
        NLLog
        ${log-lib} )

