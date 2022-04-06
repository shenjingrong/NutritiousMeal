# NutritiousMeal
营养餐项目

# Docker命令

- 打包构建：docker build -t user:v1 .
- 保存成tar归档文件：docker save -o user.tar user:v1
- 导入使用docker save命令导出的镜像：docker load < user.tar
- 启动容器：docker run -d -p 18300:18300 user:v1