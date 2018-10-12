package com.example.androidtrainassist;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sx on 2018/10/12.
 */
public class JavaUnitTestSupportTest {
    @Test
    public void addMethod() throws Exception {
        assertEquals(5, JavaUnitTestSupport.addMethod(2,1));
    }

}