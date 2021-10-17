package elk.cloud.api.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import elk.cloud.api.service.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
