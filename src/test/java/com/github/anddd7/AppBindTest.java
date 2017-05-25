package com.github.anddd7;

import com.github.anddd7.app.MessageModule;
import com.github.anddd7.app.UserModule;
import com.github.anddd7.domain.HelloWorld;
import com.github.anddd7.domain.Message;
import com.github.anddd7.domain.User;
import com.github.anddd7.domain.beans.NoScopeBean;
import com.github.anddd7.domain.beans.SingleScopeBean;
import com.github.anddd7.service.BookService;
import com.github.anddd7.service.MessageService;
import com.github.anddd7.service.UserService;
import com.github.anddd7.service.impl.MessageServiceImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

@Slf4j
public class AppBindTest {

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
