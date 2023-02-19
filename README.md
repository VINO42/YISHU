# YiShu
BTU Capstone Project
北邮网院毕业设计作品同城易书小程序后端项目
* 项目结构图生成
  ```
  ./wispha.exe layout --project-name YiShu --keys description --hide-key
  ```
  
* 项目依赖
    
    * java8
    * springboot2.7.4
    * mysql8
    * minio
    * redis
    
* 本地运行
    
    * 构建
        * 进入yishu-bootstrap 执行`mvn clean install -DskipTests`
        * 进入yishu-common 执行 `mvn clean install -DskipTests`
        * 进入yishu-module 执行 `mvn clean install -DskipTests`
        * 进入yishu-web 执行 `mvn clean install -DskipTests`
    * 执行数据库导入
        * 创建名为yishu的数据库db，字符集utf8-mb4,排序规则 `utf8mb4_general_ci`
        * 将根目录下的yishu-sql.zip1重命名为yishu-sql.zip并解压缩,导入到mysql8
    * 本地docker运行minio，并将minio的accessKey和secret在项目的dev的配置中修改
    * 本地docker运行redis，并将项目中的`application-dev.properties` 中的redis的配置项改为本地
    * 导入yishu-web项目到idea，运行SpringBoot main方法，profile参数填dev