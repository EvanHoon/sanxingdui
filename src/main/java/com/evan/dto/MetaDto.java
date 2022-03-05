/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/1/24 16:59
 **/
package com.evan.dto;

import com.evan.model.MetaDomain;

/**
 * 标签、分类列表
 */
public class MetaDto extends MetaDomain {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
