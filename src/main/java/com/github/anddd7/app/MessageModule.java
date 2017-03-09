package com.github.anddd7.app;

import com.github.anddd7.dao.MessageDao;
import com.github.anddd7.dao.impl.MessageDaoImpl;
import com.github.anddd7.domain.HelloWorld;
import com.github.anddd7.domain.beans.NoScopeBean;
import com.github.anddd7.domain.beans.SingleScopeBean;
import com.github.anddd7.service.MessageService;
import com.github.anddd7.service.impl.MessageServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;

import static com.google.inject.Scopes.NO_SCOPE;
import static com.google.inject.Scopes.SINGLETON;

/**
 * Message模块 依赖管理
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
