package com.caldremch.laboratory.basic;

import static android.content.ContentValues.TAG;

import android.util.Log;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Caldremch
 * @date 2021/12/25
 * @email caldremch@163.com
 * @describe
 **/
public class InstanceGenerationLaboratory {

    public void a(){
        InstanceGenerationLaboratory instance = new InstanceGenerationLaboratory();
        Log.d(TAG, ClassLayout.parseInstance(instance).toPrintable());
    }


}
