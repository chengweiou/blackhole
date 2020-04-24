### 框架介绍
包含了搭建restful 必要的底层通用类
#### builder
#### exception
#### rest
#### searchCondition
#### valid
#### log

### build
./gradlew jar

### publish to maven
./gradlew publish

### 本地仓库
Nexus
```
sudo chown -R 200 nexus
docker run --rm -it -d --name nexus -p 8001:8081  -v /Users/chengweiou/Desktop/docker/nexus/data:/nexus-data sonatype/nexus3
```
登录后创建仓库chengweiou
