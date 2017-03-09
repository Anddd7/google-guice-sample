## Guice简单使用

`Guice`是一个DI (依赖注入) 框架 ,和Spring的依赖注入基本相同
* 轻量级 ,启动迅速
* 使用比较简单

但是

* 只有依赖注入的功能 ,如果是企业级的程序 ,需要整合其他资源 ,Spring的生态圈还是好很多的

## 开始
* 引入依赖`@Inject` ,同Spring `@Autowired`
```java
public class MessageServiceImpl implements MessageService {
   
    @Inject
    MessageDao messageDao;

    public void sendMessage(Message msg) {
        messageDao.sendMessage(msg);
    }

    public Message receiveMessage() {
        return messageDao.receiveMessage();
    }
}
```

* 注入对象 `@Provide` ,同Spring `@Bean` ; 同时会将方法中包含的其他对象注入(nameService1,nameService2)
```java
@Provides
public List<BookService> getAllItemServices(@Named("book1") BookService nameService1, @Named("book2") BookService nameService2) {
    return of(nameService1, nameService2);
}
```

* 依赖管理 `AbstractModule` ,Spring是通过扫描注解 ,Guice则是按照模块手动控制
```java
/**
 * Message模块 依赖管理
 * 绑定接口和实现
 */
public class MessageModule extends AbstractModule {

    protected void configure() {
        final Binder binder = binder();
        //interface-impl 依赖关系
        binder.bind(MessageService.class).to(MessageServiceImpl.class).in(SINGLETON);
        binder.bind(MessageDao.class).to(MessageDaoImpl.class).in(SINGLETON);

        //单独注入 ,单例
        binder.bind(SingleScopeBean.class).in(SINGLETON);
        binder.bind(NoScopeBean.class).in(NO_SCOPE);

        //可以将已经引入的对象绑定到容器中
        binder.bind(HelloWorld.class).toInstance(helloWorld);
    }

    private HelloWorld helloWorld;
    
    public MessageModule(HelloWorld helloWorld) {
        this.helloWorld = helloWorld;
    }
}
```

* 多实现
```java
/**
 * User模块 依赖管理
 * <p>
 * 一个interface是无法绑定不同的impl的 ,可以通过两种方式
 *      使用Multibinder
 *      使用注解别名区别
 */
public class UserModule extends AbstractModule {

    protected void configure() {
        final Binder binder = binder();

        //具有多个实现的service ,会以 Set<UserService> 存在
        final Multibinder<UserService> userServiceMultibinder = Multibinder.newSetBinder(binder, UserService.class);
        userServiceMultibinder.addBinding().to(EmployeeServiceImpl.class);
        userServiceMultibinder.addBinding().to(EmployerServiceImpl.class);

        //通过名称绑定
        binder.bind(BookService.class).annotatedWith(Names.named("book1")).to(Book1ServiceImpl.class);
        binder.bind(BookService.class).annotatedWith(Names.named("book2")).to(Book2ServiceImpl.class);
    }

    /**
     * 同@Bean ,将返回值作为一个容器对象
     */
    @Provides
    public List<BookService> getAllItemServices(@Named("book1") BookService nameService1, @Named("book2") BookService nameService2) {
        return of(nameService1, nameService2);
    }
}
```

## 使用
```java
public class AppBindTest {

    private static final Logger log = LoggerFactory.getLogger(AppBindTest.class);

    private Injector injector;
    /**
     * 创建module(相当于 bean 工厂) ,可加载多个
     */
    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(
                new MessageModule(new HelloWorld("Test 初始化")),//这里的HelloWorld是手动创建 再关联到容器中的
                new UserModule());
    }

    /**
     * 测试绑定已有对象 ,HelloWorld
     */
    @Test
    public void runtimeTest() throws Exception {
        HelloWorld instance = injector.getInstance(HelloWorld.class);
        HelloWorld instance2 = injector.getInstance(HelloWorld.class);

        Assert.assertEquals(instance, instance2);
    }

    /**
     * 测试依赖注入 @Inject ,通过接口取用 ; Service中包含了Dao
     */
    @Test
    public void injectTest() throws Exception {
        MessageService instance = injector.getInstance(MessageService.class);
        instance.sendMessage(new Message("send", "土豆土豆 ,我是地瓜"));

        Message receive = instance.receiveMessage();
        log.debug(receive.toString());

        Assert.assertThat(instance, is(instanceOf(MessageServiceImpl.class)));
    }

    /**
     * 测试单例 Singleton
     */
    @Test
    public void singletonTest() throws Exception {
        SingleScopeBean instance = injector.getInstance(SingleScopeBean.class);
        SingleScopeBean instance2 = injector.getInstance(SingleScopeBean.class);

        log.debug(instance.toString());
        log.debug(instance2.toString());

        Assert.assertEquals(instance, instance2);
    }

    /**
     * 测试非单例 NoScope
     */
    @Test
    public void noScopeTest() throws Exception {
        NoScopeBean instance = injector.getInstance(NoScopeBean.class);
        NoScopeBean instance2 = injector.getInstance(NoScopeBean.class);

        log.debug(instance.toString());
        log.debug(instance2.toString());
        Assert.assertNotEquals(instance, instance2);
    }

    /**
     * 测试多重绑定 ,取出的是一个Set
     */
    @Test
    public void multiBindTest() throws Exception {
        Set<UserService> instances = injector.getInstance(
                new Key<Set<UserService>>() {
                });

        List<User> users = new ArrayList<User>();
        instances.forEach(e -> users.addAll(e.getUser()));

        users.forEach(e -> log.debug(e.toString()));

        Assert.assertEquals(4, users.size());
    }

    /**
     * 测试 @Provide 装配的对象
     */
    @Test
    public void provideTest() throws Exception {
        List<BookService> instances = injector.getInstance(new Key<List<BookService>>() {
        });
        instances.forEach(e -> log.debug(e.info()));

        Assert.assertEquals(2, instances.size());
    }
}
```
