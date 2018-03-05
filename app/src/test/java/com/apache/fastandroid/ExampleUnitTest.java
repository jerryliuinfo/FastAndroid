package com.apache.fastandroid;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static final String TAG = ExampleUnitTest.class.getSimpleName();
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 1);
        System.out.println("addition_isCorrect");
    }


    @Test
    public void testMetaData() throws Exception {

    }
}