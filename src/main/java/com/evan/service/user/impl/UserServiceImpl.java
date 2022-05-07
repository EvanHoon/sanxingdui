/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/1/23 14:27
 **/
package com.evan.service.user.impl;

import com.evan.constant.ErrorConstant;
import com.evan.dao.UserDao;
import com.evan.exception.BusinessException;
import com.evan.model.UserDomain;
import com.evan.service.user.UserService;
import com.evan.utils.DateKit;
import com.evan.utils.TaleUtils;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户相关Service接口实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;//这里会报错，但是并不影响


    @Override
    public UserDomain login(String username, String password) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);

        String pwd = TaleUtils.MD5encode(username + password);
        UserDomain user = userDao.getUserInfoByCond(username,pwd);
        if (null == user)
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);
        return user;
    }

    @Override
    public Integer register(String username, String password, String email, String screenName) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
                || StringUtils.isBlank(email) || StringUtils.isBlank(screenName)){
            return 3;
        }
//            throw BusinessException.withErrorCode("任一输入项不能为空");3

        UserDomain userTemp = userDao.getUserInfoByUsername(username);
        if (null != userTemp){
            return 4;
        }
//            throw BusinessException.withErrorCode("该用户已存在");4
        String pwd = TaleUtils.MD5encode(username + password);
        UserDomain userDomain = new UserDomain();
        userDomain.setCreated(DateKit.getCurrentUnixTime());
        userDomain.setUsername(username);
        userDomain.setPassword(pwd);
        userDomain.setEmail(email);
        userDomain.setScreenName(screenName);
        Integer resultInt = userDao.registerUser(userDomain);

        return resultInt;
    }

    @Override
    public UserDomain getUserInfoById(Integer uid) {
        return userDao.getUserInfoById(uid);
    }

    // 开启事务
    @Transactional
    @Override
    public int updateUserInfo(UserDomain user) {
        if (null == user.getUid())
            throw BusinessException.withErrorCode("用户编号不能为空");
        return userDao.updateUserInfo(user);
    }
}
