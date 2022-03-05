/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * DateTime: 2022/1/27 21:56
 **/
package com.evan.dao;

import com.evan.model.OptionsDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 网站选项相关Dao接口
 */
@Mapper
public interface OptionDao {

    /**
     * 获取网站全部信息
     * @return
     */
    List<OptionsDomain> getOptions();

    /**
     * 更新网站配置
     * @param option
     */
    void updateOptionByName(OptionsDomain option);

    /**
     * 通过名称网站配置
     * @param name
     * @return
     */
    OptionsDomain getOptionByName(String name);
}
