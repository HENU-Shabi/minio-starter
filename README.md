****MinIO client springboot starter****
[![](https://jitpack.io/v/HENU-Shabi/minio-starter.svg)](https://jitpack.io/#HENU-Shabi/minio-starter)
using MinIO like a MutableMap to persist your object.

```kotlin
//autowire the bean somehow
@Autowired
private lateinit var map: MinIOMap
```

 I am too lazy to publish this to maven central, you will have to download the jar from releases
 
 you will need to modify your _**application.properties**_ to include following fields:
 
 ```properties
min.io.host=http://127.0.0.1:9000
min.io.accessKey=minioadmin
min.io.secretKey=minioadmin
min.io.bucket=test
```
 
