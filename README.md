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
docker run --rm -it -d --name nexus -p 8001:8081  -v ~/Desktop/docker/nexus/data:/nexus-data sonatype/nexus3
```
登录后创建仓库chengweiou

### github 仓库
create token from github account
add file: ~/.gradle/gradle.properties

#### 在需要的项目加入
maven { url uri('https://maven.pkg.github.com/chengweiou/blackhole') }
implementation 'chengweiou.universe:blackhole:0.0.33'

