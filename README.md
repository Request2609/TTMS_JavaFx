<p>一年一度的软工课设再过两天就要结束了，在这里做一点总结。</p>

感觉今年软工课设用上数据库之后确实相对于大一面向过程的ｃ语言方便了不少。所以今年课设相对于大一比较简单，所以我大概提前一周就将整个项目完成了。然后将我所在组中负责的那一部分发给了组长。最后一周基本每天是在摸鱼，或者帮组员修bug。我知道我在我的项目里面造了不少重复的轮子，但我对我的要求是先实现理想情况下的功能，不考虑太多的问题，当然有必要考虑的问题会预留一些接口。
<p></p>

#####  项目介绍：
基于javafx　GUI设计，mysql进行数据持久化的C/S版影院管理系统。
##### 开发平台：
Linux Ubuntu 18.10
##### 开发工具
mysql 5.7
jetbrains idea

##### 详细设计
我将图文结合起来讲我的项目（注意别在意界面，界面设计是基于直男审美水平）先是登录界面！</p>

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190612160536994.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNjgxMjQx,size_16,color_FFFFFF,t_70)
我们看出系统总共有四个角色。下面是他们各自负责的功能模块。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190612190544331.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNjgxMjQx,size_16,color_FFFFFF,t_70)


emmm....剧目经理负责的部分挺多的（得加鸡腿）。
<p>各个功能大部分都是增删改查的操作。项目为C/S架构，总共分为４层，界面、业务逻辑、DAO层，持久化层！</p>

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190612191531948.png)
所以说DAO层以上就是Client端发送资源请求，DAO层及以下为Server端，回应界面请求。

界面层采用Javafx进行设计。以下是项目中的一些主要功能界面布局：
`选座`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190612192126955.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNjgxMjQx,size_16,color_FFFFFF,t_70)
`剧目管理`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190612192251563.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNjgxMjQx,size_16,color_FFFFFF,t_70)
`演出计划管理`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190612192419541.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNjgxMjQx,size_16,color_FFFFFF,t_70)

业务逻辑层主要接收来自界面层用户的输入信息的，并将信息传给DAO层。
DAO层接收到业务逻辑层的数据生成sql代码，将相应的sql语句传给持久化层。
持久化层连接数据库，执行sql语句，获得结果集，返回给DAO层，DAO层返回给业务逻辑层。业务逻辑层返回给界面层。
四层交互就是这样。在项目中体现：



![在这里插入图片描述](https://img-blog.csdnimg.cn/2019061219352638.png)


idao中设计dao层的接口。model是所有数据类（用户类，票类.....）,util就是持久化层，service 为业务逻辑层，view为界面层。

C/S设计就是比较简单。

`难点总结`
<p>
１、票的加锁
</p>
<p>
这里一些数据成员的增删改查我想说的是关于乐观锁，这个是老师要求使用的，但我感觉使用上也并没有什么用。但考虑到这是一个比较重要的知识点，还是说一下吧！乐观锁主要用于关于买票业务。在并发情况下，多个售票员访问了一张票，并进行了相应的修改？
所以这种情况应该加锁，如何加呢？有两种锁可供选择：悲观锁和乐观锁。

悲观锁：先说一下大概是什么个东西？用户每次访问数据库数据时，都认为用户会修改所访问的数据，所以在访问的时候加上锁，在当前用户访问期间其他用户不能访问。知道当前用户操作完成才可访问。

项目中主要通过数据库提供的关键字实现，采用行级锁，在多个售票员访问同一张票的情况下，先对数据库表的行加锁的用户向访问，其他用户都不能访问，知道上锁的售票员操作完成，释放锁，其他售票员才可以访问。

乐观锁：用户访问数据库数据时相信用户不会修改表中的数据，将数据信息发给用户，只有在用户修改表中的数据时才进行锁保护。就相当于实现数据的版本控制。

项目实现乐观锁可以给票中加一列version(可以自己随便取)，类型我采用long，也可使用timestamp(时间戳类型)，起始值为１，在之后操作中，要是修改某行数据，现将这一行数据提取出来，得到version，在update时，再通过version判断行中数据是否被修改，如果没被修改，则给version加1，更新成功。否则这次更新将失败。我项目中实现如下例子所示：
```
select * from ticket where ticket_id = #(ticket_id);
long version = ticket.version ;
update ticket set version+=1, ticket_info=want_update_ticket_info where (ticket.version=version and ticket.id = #(ticket_id)) ;
```
</p>
<p>２、演出计划时间冲突的处理</p>
我觉得我现在还不能将所有的情况考虑周全，安排一场演出计划涉及到的情况特别多，首先你得需要演出厅，下面就是演出剧目是什么，演出时间什么时候。那么问题来了？
演出厅是否被占用。演出剧目是否已经被安排？当然我解决了这两个问题，肯定不止这些。

演出剧目被安排，我这里的处理是已经被安排的剧目无法再安排演出计划。
演出厅占用的话，找到占用演出厅所有的演出计划，查看所设置的上映时间是否会和已经存在的演出计划冲突，要是不冲突的话，可以给该演出厅安排演出计划，否则就不能安排。
</p>
<p>３、演出计划过期删除</p>
<p>这是一个很实际的问题，剧目经理干的事有点多，他支撑起了整个影院的运营。可删除演出计划这个事他们不可能每天将所有的演出计划查看一遍有过期的就删除。这不行，和同学讨论在安排演出计划的时候将整个演出计划表产看一遍看有没有过期的。这也是一种解决方法，我解决这一问题是通过定时器。开个线程，安排定时任务，在一定时间内后台自动检查一遍演出计划表，发现过期的就将其删除。并将相应演出厅状态一修改，票状态修改，座位状态修改。</p>
<p>４、图片展示</p>
<p>可以看出整个项目完成下来，图片是必不可少的元素，图片是从网上下载的，整个项目中的图片都是放到个人租的服务器上的，数据库中存的是服务器中图片的url，在项目运行期间要加载图片，并显示在界面。</p>


##### 数据库表的物理模型设计
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190613103934457.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNjgxMjQx,size_16,color_FFFFFF,t_70)
一些表在开发中没有用到大致就是这样，用到那些数据主要参考src代码中的Dao层操作那些数据。

##### 使用方法
在数据库中添加管理员（access为0），登录管理员在系统中添加其他用户信息，剧目经理权限为1，销售员为2，财务经理权限为6，其他用户协作运营整个系统。
##### 总结<p>
一年一度的TTMS又宣布到尾声了，总的项目写下来，造了许多重复的轮子，面向对象的思想还是比较缺！主要是刚开始也没好好设计，初衷是先实现功能。最后发现许多相似的功能代码可以不断复用。最后复习一下软件设计的流程：

`问题定义->可行性研究->需求分析->总体设计->详细设计->编码和单元测试->综合测试->软件维护`
