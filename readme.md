### 策略模式
---
#### Strategy-Pattern1
利用继承提供Duck的行为，可以实现，但有一些缺点：
1. 改变会牵一发而动全身，造成其它鸭子不想要的改变
2. 有重复代码，比如在多个具体类中都出现了这样的方法——覆盖父类定义，什么都不做
3. 很难知道所有鸭子的所有行为
4. 行为不容易改变
---
#### Strategy-Pattern2
- 已解决问题
  - 把可能变化的部分抽取出来到接口，具体类需要什么功能自己实现相应接口，这样就不会造成原来在基类牵一发而动全身的情况
- 新问题
  1. 重复代码很多，比如在红头鸭和绿头鸭的fly方法、quack方法都是重复的
  2. 在测试类里声明一个具体的鸭子的时候，不可以使用父类引用指向子类对象，因为有些方法定义在子类中（实现接口的部分方法）