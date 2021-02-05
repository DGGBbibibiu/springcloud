package com.learn.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.learn.springboot.dao.PaymentDao;
import com.learn.springboot.entity.CommonResult;
import com.learn.springboot.entity.Payment;
import com.learn.springboot.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author xiaofan.li
 * @version 1.0
 * @desc
 * @date 2021/1/22 16:31
 */

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    private SqlSessionFactory sqlSessionFactory;

    private PlatformTransactionManager transactionManager;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment)
    {
        int result = paymentService.create(payment);
        log.info("*****插入结果："+result);

        if(result > 0)
        {
            return new CommonResult(200,"插入数据库成功,serverPort: "+serverPort,result);
        }else{
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getInfoById(@PathVariable("id") Long id){
        System.out.print("-------start search");
        Payment payment = paymentService.getPaymentById(id);
        return new CommonResult(200, String.format("查询ID为 %s 的数据成功, 服务端口： %s", id, serverPort), JSONObject.toJSON(payment));
    }


    /**
     *  MyBatis 提供了一个叫 org.apache.ibatis.cursor.Cursor 的接口类用于流式查询，这个接口继承了 java.io.Closeable 和 java.lang.Iterable 接口，由此可知：
     *  Cursor 是可关闭的；
     *  Cursor 是可遍历的。
     * 除此之外，Cursor 还提供了三个方法：
     *     isOpen()：用于在取数据之前判断 Cursor 对象是否是打开状态。只有当打开时 Cursor 才能取数据；
     *     isConsumed()：用于判断查询结果是否全部取完。
     *     getCurrentIndex()：返回已经获取了多少条数据
     * 因为 Cursor 实现了迭代器接口，因此在实际使用当中，从 Cursor 取数据非常简单:
     *      cursor.forEach(rowObject -> {...});
     * @param limit
     * @throws Exception
     */
    @GetMapping(value = "/payment/scan/0/{limit}")
    public void scanPayment(@PathVariable("limit") int limit) throws Exception{
        try(Cursor<Payment> cursor = paymentService.scan(limit)) {
            cursor.forEach(payment -> {
                System.out.print(JSONObject.toJSON(payment));
            });
        }
    }

    /**
     * 在取数据的过程中需要保持数据库连接，而 Mapper 方法通常在执行完后连接就关闭了，因此 Cusor 也一并关闭了。
     * 所以保持数据库连接打开
     * 1 处我们开启了一个 SqlSession （实际上也代表了一个数据库连接），并保证它最后能关闭；
     * 2 处我们使用 SqlSession 来获得 Mapper 对象。这样才能保证得到的 Cursor 对象是打开状态的。
     * 方案一：SqlSessionFactory
     * @param limit
     * @throws Exception
     */
    @GetMapping(value = "/payment/scan/1/{limit}")
    public void scanPayment1(@PathVariable("limit") int limit) throws Exception{
        try(SqlSession sqlSession = sqlSessionFactory.openSession();
            Cursor<Payment> cursor = sqlSession.getMapper(PaymentDao.class).scan(limit)
        ) {
            cursor.forEach(payment -> {});
        }
    }

    /**
     * 1 处我们创建了一个 TransactionTemplate 对象（此处 transactionManager 是怎么来的不用多解释，本文假设读者对 Spring 数据库事务的使用比较熟悉了）
     * 2 处执行数据库事务，而数据库事务的内容则是调用 Mapper 对象的流式查询。注意这里的 Mapper 对象无需通过 SqlSession 创建。
     * @param limit
     * @throws Exception
     */
    @GetMapping(value = "/payment/scan/2/{limit}")
    public void scanPayment2(@PathVariable("limit") int limit) throws Exception{
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            try (Cursor<Payment> cursor = paymentService.scan(limit)) {
                cursor.forEach(foo -> { });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @GetMapping(value = "/payment/scan/3/{limit}")
    @Transactional
    public void scanPayment3(@PathVariable("limit") int limit) throws Exception{
        try(Cursor<Payment> cursor = paymentService.scan(limit)) {
            cursor.forEach(payment -> {
                System.out.println(JSONObject.toJSON(payment));
            });
        }
    }
}
