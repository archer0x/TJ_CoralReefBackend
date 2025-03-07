传递给python的JSON格式：
````
{
   "image_path": "006.jpg"
   "save_path": "D:/Code/backend/UploadPhoto"
}
````
返回值以json格式，如下：

````
{
   "res": [
     [
         "http://localhost:8080/SourcePhoto/1.jpg",
         "http://localhost:8080/ResultPhoto/result1.jpg"
     ],
     [
         "http://localhost:8080/SourcePhoto/1.jpg",
         "http://localhost:8080/ResultPhoto/result1.jpg"
     ]
          ]
}