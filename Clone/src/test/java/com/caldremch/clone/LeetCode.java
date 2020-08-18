package com.caldremch.clone;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author Caldremch
 * @date 2020-08-16
 * @email caldremch@163.com
 * @describe
 **/
public class LeetCode {


    @Test
    public void twoSumTest() {
        int[] nums = {3, 3, 3};
        int target = 6;
        int[] result = twoSum(nums, target);
        System.out.println(result[0] + "," + result[1]);
        Assert.assertEquals(result[0], 0);
        Assert.assertEquals(result[1], 1);
    }

    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        HashMap<Integer, Integer> check = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            check.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            if (check.containsKey(target - nums[i])) {
                int value = check.get(target - nums[i]);
                if (value == i) {
                    continue;
                }
                result[0] = i;
                result[1] = value;
                break;
            }
        }
        return result;
    }

}
