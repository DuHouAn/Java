# Maven

Maven  是基于**项目对象模型（POM）**,可以通过一小段描述信息来管理项目的构建、报告和文档的软件项目管理工具。

[Maven 下载](http://maven.apache.org/download.cgi)

[Maven 官网](https://maven.apache.org/)



## Maven 目录结构

```html
src
	|- main
		|--java
			|-- packages
		|-- resources
	|- test
		|--java
			|-- packages
```



## Maven 坐标和仓库

### 坐标

- groupID

  **项目组织**的唯一标识符，实际对应 java 的包的结构，即 main目录里 java 的目录结构。

- artifactID

  **项目**的唯一标识符，实际对应项目的名称，就是项目根目录的名称。

groupId 和 artifactId 被统称为**坐标**。构件通过坐标作为其唯一标识。

groupId 一般为 `组织名+公司地址反写+项目名`，比如 `com.southeast.myprojects`

artifactId 一般为 `项目名-模块名`，比如`myprojects-demo`

则相应的 package 为 `com.southeast.myprojects.demo`

### 镜像仓库

setting.xml 文件中配置：

```html
<mirror>
    <id>maven.net.cn</id>
    <mirrorOf>central</mirrorOf>
    <name>central mirror in china</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
</mirror>
```

### 修改本地仓库位置

setting.xml 文件中配置：

```html
<localRepository>F:/Java/apache-maven-3.6.2-bin/repo</localRepository>
```



## Maven 常用构建命令

### 1. 查看版本

```html
mvn - v 
```

### 2. 编译

```html
mvn compile
```

### 3. 测试

```html
mvn test
```

### 4. 打包

```html
mvn package
```

### 5. 清除 target

```html
mvn clean
```

### 6. 安装 jar

```html
mvn install
```

### 7. 自动创建目录骨架

```html
mvn archetype:generate
# 按照提示进行选择
```

```html
mvn archetype:generate -DgroupId=组织名，公司网址反写+项目名
	-DartifactId=项目名-模块名
	-Dversion=版本号
	-Dpackage=代码所存在的包名
```



## pom.xml 解析

- 指定当前 pom 版本

  ```html
  <modelVersion>
  ```

- 坐标

  ```html
  <groupId>反写公司网址+项目名</groupId>
  <artifactId>项目名+模块名</artifactId>
  <!-- 
      第一个 0 表示大版本号
      第二个 0 表示分支版本号
      第三个 0 表示小版本号
      snapshot 快照
      alpha 内部测试
      beta 公测
      Release 稳定
      GA 正式发布
  -->
  <version>0.0.1snapshot</version>
  <!-- 
      默认是 jar
  	war
  	zip
  	pom
  -->
  <packaging>jar</packaging>
  <!--
  	项目描述名
  -->
  <name></name>
  <!--
  	项目地址
  -->
  <url></url>
  ```

- 依赖列表

  ```html
  <dependencies>
      <dependency>
      	<groupId></groupId>
  		<artifactId></artifactId>
          <version></version>
          <type></type>
          <!-- 
  			依赖范围：
  			compile 默认的范围，编译测试运行都有效
  			provided 编译测试时有效
  			runtime 测试运行时有效
  			test 只在测试时有效
  			system 与本机系统相关联，可移植性差
  			import 导入的范围，只使用在 dependencyManagement 中，表示从其他的 pom 中导入 dependency 的配置
  		-->
          <scope>test</scope>
          <!-- 
  			设置依赖是否可选
  		-->
          <optional></optional>
          <!-- 
  			排除依赖
  		-->
          <exclusions>
              <exclusion>
              	  <!--
  					坐标
  				  -->
              </exclusion>
          </exclusions>
      </dependency>
  </dependencies>
  ```

- 依赖管理

  ```html
  <dependencyManagement>
      <dependencies>
          <dependency></dependency>
      </dependencies>
  </dependencyManagement>
  ```

- build 标签

  ```html
  <build>
      <!--
  		插件列表
  	-->
  	<plugins>
          <plugin>
          	<groupId></groupId>
  			<artifactId></artifactId>
          	<version></version>
          </plugin>
      </plugins>
  </build>
  ```

- parent 标签（继承）

  ```html
  <parent>
  	<!--
  		坐标
  	-->
  </parent>
  ```

- modules 标签（聚合）

  ```html
  <modules>
  	<module></module> 
  </modules>
  ```

## 依赖冲突

### 1. 短路优先

```html
路径1：A -> B -> C -> X(jar)
路径2：A -> D -> X(jar)
```

这里优先选择路径 2。

### 2. 先声明先优先

如果路径长度相同，则谁先声明，先解析谁。

、





