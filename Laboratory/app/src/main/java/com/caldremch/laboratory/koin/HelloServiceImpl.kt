package com.caldremch.laboratory.koin

/**
 * Created by Leon on 2022/6/14
 */
class HelloServiceImpl : IHelloService {
    override fun hello(): String {
        return "hello koin"
    }
}