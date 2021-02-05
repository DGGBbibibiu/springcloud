package com.learn.springboot.service.impl;

import com.learn.springboot.dao.PaymentDao;
import com.learn.springboot.entity.Payment;
import com.learn.springboot.service.PaymentService;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiaofan.li
 * @version 1.0
 * @desc
 * @date 2021/1/22 17:15
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }

    @Override
    public Cursor<Payment> scan(int limit) {
        return paymentDao.scan(limit);
    }
}
