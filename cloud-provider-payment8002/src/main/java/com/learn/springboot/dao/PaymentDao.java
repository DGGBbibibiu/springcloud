package com.learn.springboot.dao;

import com.learn.springboot.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

@Mapper
public interface PaymentDao {

    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);

    @Select("select * from payment limit #{limit}")
    Cursor<Payment> scan(@Param("limit") int limit);
}
