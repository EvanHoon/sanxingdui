/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/1/24 16:59
 **/
package com.evan.dto.cond;

/**
 * Meta查询条件
 */
public class MetaCond {

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
