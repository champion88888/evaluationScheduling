package com.mediaocean.test;
import org.json.JSONObject;
import org.junit.Test;
import com.mediaocean.scheduler.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by abhishek.sing on 06/09/17.
 */
public class TestClass {
    @Test
    public void testNumberOfDays(){
        EventScheduler eventScheduler = new EventScheduler();
        JSONObject jsonObject = new JSONObject(eventScheduler.getMaxDays(3));
        System.out.print(jsonObject.getInt("days"));
        assertEquals(11, jsonObject.getInt("days"));
        jsonObject = new JSONObject(eventScheduler.getMaxDays(-1));
        System.out.print(jsonObject.getInt("days"));
        assertEquals(-1, jsonObject.getInt("days"));
        jsonObject = new JSONObject(eventScheduler.getMaxDays(1));
        System.out.print(jsonObject.getInt("days"));
        assertEquals(0, jsonObject.getInt("days"));


    }

}
