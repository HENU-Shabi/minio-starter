****MinIO client springboot starter****

using MinIO lika an array to persist your object.

```kotlin
        //delete an object by assign Unit to the element of corresponding index
        client["test"] = Unit
        //persist a object with header and content type
        client["test"] = "test str" withHeader mutableMapOf("a" to "b") withContentType "application/json"
        //persist a object without header or content type
        client["pure"] = "pure str"
        //get a persisted object by its index,the return type is a Tripe which can be deconstructed,containing:
        //(the object,response headers,content type)
        val (str, map, contentType) = client["test"]
```

 I am too lazy to publish this to maven central, you will have to download the jar from releases