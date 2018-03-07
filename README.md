# smartframework
一个轻量级的JavaWeb框架
内置 IOC、AOP、MVC 等特性
基于 Servlet 3.0 规范
使用 Java 注解取代 XML 配置
1.依赖注入的功能：能够自动捕获带有@Inject注解的Bean,通过反射创建Bean的实例，赋值给那个字段<br>
2.AOP的功能：底层使用了Cglib动态代理的技术实现了AOP特性，web应用只需要继承AspectProxy代理类，使用@Aspect注解指定切面类，然后重写before、after等方法，便可实现和SpringAOP类似的功能。<br>
3.MVC的功能：和SpringMVC类似，所有的HTTP请求首先经过前端控制器DispatcherServlet进行转发，根据Request找到对应的Handler，执行对应的方法，根据返回值是View对象还是Data对象分别返回JSP页面和Json对象
