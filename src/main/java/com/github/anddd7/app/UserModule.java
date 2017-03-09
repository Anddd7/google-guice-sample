package com.github.anddd7.app;

import com.github.anddd7.service.BookService;
import com.github.anddd7.service.UserService;
import com.github.anddd7.service.impl.Book1ServiceImpl;
import com.github.anddd7.service.impl.Book2ServiceImpl;
import com.github.anddd7.service.impl.EmployeeServiceImpl;
import com.github.anddd7.service.impl.EmployerServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;

/**
 * User模块 依赖管理
 * <p>
 * 一个interface是无法绑定不同的impl的 ,可以通过两种方式
 * 使用Multibinder
 * 使用注解别名区别
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
