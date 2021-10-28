package encry.entity;

import lombok.Data;

/**
 * @Classname DemoDate
 * @Date 2021/8/27 10:33
 * @Created by chenrujia
 * @Description 测试实体类
 */
@Data
public class DemoMessage {
    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 地址
     */
    private String address;
}
