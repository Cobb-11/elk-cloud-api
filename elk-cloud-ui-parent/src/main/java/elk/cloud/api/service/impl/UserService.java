package elk.cloud.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import elk.cloud.api.service.dao.UserDao;
import elk.cloud.api.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    @Autowired
    private UserDao userDao;

    public Integer update(User user){
        Wrapper<User> wrapper = new UpdateWrapper();

        return userDao.update(user,wrapper);
    }

    public User selectByUserName(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",username);
        return userDao.selectOne(wrapper);
    }

    public User selectById(String id){
        return userDao.selectById(id);
    }
}
