package com.fpx.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class connnectTest {

    private String host = "192.168.1.105";

    private int port = 30001;

    private String ruleKey = "sentinel.rules.flow.ruleKey";
    private String channel = "sentinel.rules.flow.channel";

   private  String flowRulesJson =
            "[{\"resource\":\"test\", \"limitApp\":\"default\", \"grade\":1, \"count\":\"0.0\", \"strategy\":0, "
                    + "\"refResource\":null, "
                    +
                    "\"controlBehavior\":0, \"warmUpPeriodSec\":10, \"maxQueueingTimeMs\":500, \"controller\":null}]";

    private final RedisClient redisClient = RedisClient.create(RedisURI.Builder.redis(host, port)
            .build());


    @Test
    public void testConnect(){
        RedisCommands<String, String> stringRedisCommands = redisClient.connect().sync();
        String ok = stringRedisCommands.set(ruleKey, flowRulesJson);
        Assert.assertEquals("OK", ok);
    }

    @Test
    public void testSub(){
        RedisCommands<String, String> subCommands  = redisClient.connect().sync();
        subCommands.multi();
        subCommands.set(ruleKey, flowRulesJson);
        subCommands.publish(channel, flowRulesJson);
        subCommands.exec();
    }

    @After
    public void clearResource() {
        RedisCommands<String, String> stringRedisCommands = redisClient.connect().sync();
        stringRedisCommands.del(ruleKey);
        redisClient.shutdown();
    }


}
