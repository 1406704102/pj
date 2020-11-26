### 系统
* 查看端口占用 lsof -i:8000
* 结束某个进程 kill -9 {PID}
* 查看磁盘情况 df -h
* 清除nohup.out文件内容 echo "" > nohup.out
* 运行sh文件 sh xxxx.sh
* 后台运行jar并且不打印日志(>/dev/null 2>&1)

        nohup java -jar eladmin-system-2.5-8001.jar >/dev/null 2>&1 --spring.profiles.active=prod &

* 下载文件 wget http://nodejs.org/dist/v8.11.1/node-v8.11.1-linux-x64.tar.gz
* 解压 tar -zxvf node-v8.11.1-linux-x64.tar.gz
* 重命名文件夹 mv node-v8.11.1-linux-x64 node
* 编辑文件 vim ****
* 创建文件夹 mkdir 文件夹

