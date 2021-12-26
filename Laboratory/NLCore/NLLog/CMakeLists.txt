cmake_minimum_required(VERSION 3.10.2)
project(NLLog C)

set(CMAKE_C_STANDARD 99)

add_library(NLLog nl-log.c nl-log.h)
