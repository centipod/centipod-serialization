package io.centipod.serialization;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SerializationUtilTest {

    @Test
    void testSerializationUtil() {

        Map<String,String> map = Collections.singletonMap("name", "value");
        String serialized = SerializationUtil.serialize(map);
        Map<String,String> clone = SerializationUtil.deserialize(serialized);
        Assertions.assertEquals(map, clone, "Objects are not identical");
    }
}
