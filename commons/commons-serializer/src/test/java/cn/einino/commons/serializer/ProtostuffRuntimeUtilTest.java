package cn.einino.commons.serializer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by einino on 2016/11/18.
 */
public class ProtostuffRuntimeUtilTest {

    @Test
    public void testSerializeAndDeserialize() {
        TestData data = new TestData();
        data.setAge(32);
        data.setName("Tom");
        data.setMan(true);
        data.setPhone(135345654308L);
        byte[] buf = ProtostuffRuntimeUtil.serialize(data);
        System.out.println(buf.length);
        data = ProtostuffRuntimeUtil.deserialize(buf, TestData.class);
        System.out.println(data);
    }

    @Test
    public void testSerializeAndDeserializeList() {
        List<TestData> list = new ArrayList<>(2);
        TestData data = new TestData();
        data.setAge(32);
        data.setName("Tom");
        data.setMan(true);
        data.setPhone(135345654308L);
        list.add(data);
        data = new TestData();
        data.setAge(23);
        data.setName("Jenny");
        data.setMan(false);
        data.setPhone(135345654352L);
        list.add(data);
        byte[] buf = ProtostuffRuntimeUtil.serializeList(list, TestData.class);
        System.out.println(buf.length);
        list = ProtostuffRuntimeUtil.deserializeList(buf, TestData.class);
        for (TestData td : list) {
            System.out.println(td);
        }
    }

    static class TestData {

        private String name;
        private int age;
        private boolean man;
        private long phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isMan() {
            return man;
        }

        public void setMan(boolean man) {
            this.man = man;
        }

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "TestData{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", man=" + man +
                    ", phone=" + phone +
                    '}';
        }
    }
}
