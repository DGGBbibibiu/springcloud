package com.learn.springboot.service;

import com.learn.springboot.entity.Payment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;

public interface PaymentService {

    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);

    Cursor<Payment> scan(int limit);
}
