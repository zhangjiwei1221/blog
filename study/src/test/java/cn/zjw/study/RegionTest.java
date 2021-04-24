package cn.zjw.study;

import cn.zjw.study.entity.Region;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.google.gson.Gson;
import org.junit.Test;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RegionTest
 *
 * @author zjw
 * @date 2021/4/9
 */
public class RegionTest {

    @Test
    public void regionTest() throws IOException {
        File file = Paths.get("H:\\java\\blog\\study\\src\\test\\resources\\region.json").toFile();
        InputStream is = new FileInputStream(file);
        String str = new String(is.readAllBytes());
        Map map = new Gson().fromJson(str, Map.class);
        Set<String> set = new HashSet<>(Arrays.asList("北京市", "天津市", "重庆市", "上海市"));
        List<Region> list = new ArrayList<>();
        map.forEach((key, val) -> {
            if (set.contains(key)) {
                Region region = new Region();
                long id = getId();
                region.setId(id);
                region.setName((String) key);
                region.setLevelType(1);
                region.setParentId(0L);
                list.add(region);
                Region region1 = new Region();
                long id1 = getId();
                region1.setId(id1);
                region1.setName((String) key);
                region1.setLevelType(2);
                region1.setParentId(id);
                list.add(region1);
                Map m1 = (Map) val;
                m1.forEach((key1, val1) -> {
                    List list1 = (List) val1;
                    for (Object o : list1) {
                        String m2 = (String) o;
                        Region region2 = new Region();
                        long id2 = getId();
                        region2.setId(id2);
                        region2.setName(m2);
                        region2.setLevelType(3);
                        region2.setParentId(id1);
                        list.add(region2);
                    }
                });
                return;
            }
            Region region = new Region();
            long id = getId();
            region.setId(id);
            region.setName((String) key);
            region.setLevelType(1);
            region.setParentId(0L);
            list.add(region);
            Map m = (Map) val;
            m.forEach((key1, val1) -> {
                Region region1 = new Region();
                boolean flag = false;
                if (((String) key1).contains("划")) {
                    flag = true;
                }
                long id1 = getId();
                region1.setId(id1);
                region1.setName((String) key1);
                region1.setLevelType(2);
                region1.setParentId(id);
                if (!flag) {
                    list.add(region1);
                }
                List list1 = (List) val1;
                for (Object o : list1) {
                    String m1 = (String) o;
                    Region region2 = new Region();
                    long id2 = getId();
                    region2.setId(id2);
                    region2.setName(m1);
                    region2.setLevelType(flag ? 2 : 3);
                    region2.setParentId(id1);
                    list.add(region2);
                }
            });
        });
        File sql = new File("C:\\\\Users\\\\zjw\\\\Desktop\\\\sql.txt");
        FileWriter fw = new FileWriter(sql);
        AtomicInteger i = new AtomicInteger();
        System.out.println(list.size());
        list.forEach(a -> {
            i.getAndIncrement();
            if (i.get() >= 3436) {
                System.out.println(a);
            }
        });
    }

    @Test
    public void snowTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getId());
        }
    }

    private final DefaultIdentifierGenerator defaultIdentifierGenerator = new DefaultIdentifierGenerator();

    private Long getId() {
        return defaultIdentifierGenerator.nextId(null);
    }

}
