package com.apache.fastandroid.tink;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jerry on 2020-05-03.
 */
public class TinkTest {

    public void test(Context context){
        int i = 10;
        int a = 1;

        Toast.makeText(context, "shit"+ i /a,Toast.LENGTH_LONG).show();
    }
}
