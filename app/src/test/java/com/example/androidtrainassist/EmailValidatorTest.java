package com.example.androidtrainassist;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;
/**
 * Created by sx on 2018/10/12.
 */

@RunWith(MockitoJUnitRunner.class)
public class EmailValidatorTest {
    private static final String FAKE_STRING = "HELLO WORLD";

    @Mock
    Context mMockContext;

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue(){
        // Given a mocked Context injected into the object under test...
        when(mMockContext.getString(R.string.app_name))
                .thenReturn("lala");

        // ...then the result should be the expected one.
        assertThat("HELLO WORLD", is((mMockContext.getString(R.string.app_name))));

    }
}
