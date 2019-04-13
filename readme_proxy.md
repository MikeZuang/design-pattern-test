#### 代理模式——控制对象访问
   - Proxy-Pattern1——没有使用远程代理
   - Proxy-Pattern2——使用远程代理，但运行不正常
   - Proxy-Client——Proxy-Pattern2客户端
   - Proxy-Pattern3——虚拟代理
   - Proxy-Pattern4——动态代理
   - Proxy-Pattern5——代理模式详解
---
##### Proxy-Pattern2——远程代理   
调用远程对象的方法
   - 整体流程
     1. 本地JVM
     2. 客户端——>本地代理对象（处理网络通信）
     3. 服务端JVM
     4. 服务器——>真正对象方法执行
   - 具体过程：
     1. 本地代理对象通过客户辅助对象来工作
     2. stub客户辅助对象，连接服务器，传递变量、方法名，等待服务器返回
     3. 服务器端通过服务辅助对象来实现远程调用支持
     4. skeleton服务辅助对象，通过Socket获取客户辅助对象传递过来的变量、方法名，然后调用真正的服务对象上的方法，并接受返回值打包返回客户辅助对象
   - 使用jdk内置的RMI不需要我们写I/O代码；RMI提供了客户辅助对象和服务辅助对象；RMI提供了查找服务（lookup service）,用来寻找和访问远程对象。   
编码实现：
     1. 定义服务接口（stub和真正的服务都实现此接口）
     2. 实现服务接口
     3. 使用rmic产生stub和skeleton，注意不要添加.class   
rmic MyServiceImpl
     4. 启动 rmiregistry ，等待注册
     5. 运行服务实现类   
创建一个服务的实例，并注册到RMIregisty；运行就会把stub注册到rmiregisty   
这样客户端就可以在服务端查找到代理对象并返回给客户端
---
##### Proxy-Pattern3——虚拟代理
常常作为创建开销大的对象的代表   
内存开销大、网络延迟的时候可以使用虚拟代理