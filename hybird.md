## hybird 技术如何实现？
hybird的技术实现依赖于以下两点：
- file和schema协议
- webview

## 如何利用 file\schema 协议和 webview 控件来实现？
webview加载页面有两种方式：
- 加载网路页面，只需传入http的url即可
- 加载本地页面，这里细分为两种：
-- 加载asserts目录下面的html,这样访问本地离线文件，不用进行网络请求，可以减少用户的数据流量。
-- 加载缓存到本地的页面，主要用来做页面的离线缓存，如将html文件缓存存储到本地的文件目录下