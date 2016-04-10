# newClient-simple
这是一个简陋的新闻客户端，只是用ListView实现了最基本功能，由于是在自己电脑开的tomcat服务器，所以连接地址使用时要修改。


本项目使用了SmartImageView库来实现获取网络图片，这个过程本身就是异步。不必再进行处理，也不必担心线程阻塞网络获取到的图片都进行了缓存的处理。会在程序的cache目录下建/web_image_cache/，图片存在这里上次使用的时候，如果缓存图片已经存在，则不再从网络获取图片。

下面是运行效果图：

![image](http://i.imgur.com/0NcGJz0.jpg)
