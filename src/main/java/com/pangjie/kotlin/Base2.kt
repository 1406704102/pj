package com.pangjie.kotlin

import java.io.OutputStreamWriter

import java.io.BufferedWriter

import java.io.BufferedReader
import java.io.InputStreamReader

import java.net.ServerSocket
import java.net.Socket


fun main() {
    val ss = ServerSocket(8888)
    println("启动服务器....")
    val s: Socket = ss.accept()

    val br = BufferedReader(InputStreamReader(s.getInputStream()))
    //读取客户端发送来的消息
    //读取客户端发送来的消息
    val mess = br.readLine()
    println("客户端：$mess")
    val bw = BufferedWriter(OutputStreamWriter(s.getOutputStream()))
    bw.write("""
    $mess
    
    """.trimIndent())
    bw.flush()
}