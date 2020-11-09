### 系统
* 查看端口占用 lsof -i:8000
* 结束某个进程 kill -9 {PID}
* 查看磁盘情况 df -h
* 清除nohup.out文件内容 echo "" > nohup.out
* 运行sh文件 sh xxxx.sh
* 后台运行jar并且不打印日志(>/dev/null 2>&1)

        nohup java -jar eladmin-system-2.5-8001.jar >/dev/null 2>&1 --spring.profiles.active=prod &


