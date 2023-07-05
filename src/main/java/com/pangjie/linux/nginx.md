### nginx
 * 配置
 
        server {  #前台
            listen                  443 default_server;  
            server_name             cm.xmay.cc;
            # 证书文件，这里使用了 fullchain.cer 通过 acme.sh 生成的泛域名证书  
            ssl_certificate         /etc/nginx/cret/4036691__xmay.cc.pem;  
            # 私钥文件  
            ssl_certificate_key     /etc/nginx/cret/4036691__xmay.cc.key;
            client_max_body_size 20M;
            client_body_buffer_size 20M;
            proxy_buffer_size 10M;
            proxy_buffers 100 10M;
         	root  /etc/nginx/dist/web;  #dist上传的路径
                # 避免访问出现 404 错误
                location / {
                    try_files $uri $uri/ @router;
                    index  index.html;
               	 }
                location @router {
                    rewrite ^.*$ /index.html last;
                }  
        } 
        
        
        server {  #正在使用的9-24
            listen                  443 ssl;  
            server_name             cmtv.xmay.cc;
            # 证书文件，这里使用了 fullchain.cer 通过 acme.sh 生成的泛域名证书  
            ssl_certificate         /etc/nginx/cret/4036691__xmay.cc.pem;  
            # 私钥文件  
            ssl_certificate_key     /etc/nginx/cret/4036691__xmay.cc.key;  
            client_max_body_size 20M;
            client_body_buffer_size 20M;
            proxy_buffer_size 10M;
            proxy_buffers 100 10M;
            location /dist/ {  
        	    root /etc/nginx/dist;
        	    autoindex on;
             }  
            location / {  
        	    proxy_pass http://127.0.0.1:8001/;
             }  
        }
        
 
 * nginx 重启/运行 nginx -s reload