package com.pangjie.kotlin

import java.io.*
import java.net.Socket


class Base {



    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    fun sum2(a: Int, b: Int) = a + b
}

fun main(args: Array<String>) {
    val s = Socket("127.0.0.1", 8888)

    //构建IO

    //构建IO
    val `is` = s.getInputStream()
    val os = s.getOutputStream()

    val bw = BufferedWriter(OutputStreamWriter(os))
    //向服务器端发送一条消息
    //向服务器端发送一条消息
    bw.write("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦\n")
    bw.flush()

    //读取服务器返回的消息

    //读取服务器返回的消息
    val br = BufferedReader(InputStreamReader(`is`))
    val mess = br.readLine()
    println("服务器：$mess")
}
