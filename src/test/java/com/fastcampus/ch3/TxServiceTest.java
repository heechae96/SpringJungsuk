package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class TxServiceTest {
    @Autowired
    TxService txService;

    // 반드시 a1 DB를 비운 상태로 해야함!

//    @Test
//    public void insertA1WithoutTxTest() throws Exception{
//        txService.insertA1WithoutTx();
//    }

    @Test
    public void insertA1WithTxTest() throws Exception{
//        txService.insertA1WithTxSuccess();
//        txService.insertA1WithTxFail();
        txService.insertA1WithTx();
    }
}