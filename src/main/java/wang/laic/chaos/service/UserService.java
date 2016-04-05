package wang.laic.chaos.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.github.pagehelper.PageHelper;

import wang.laic.chaos.mapper.UserMapper;
import wang.laic.chaos.model.User;
import wang.laic.chaos.model.UserCriteria;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public List<User> getAll(User user) {
		if (user.getPage() != null && user.getRows() != null) {
            PageHelper.startPage(user.getPage(), user.getRows(), "id");
        }
		UserCriteria ue = new UserCriteria();
		return userMapper.selectByExample(ue);
//        return userMapper.selectAll();
	}
	
    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
    
    public User findUserByLoginName(String username) {
    	UserCriteria uc = new UserCriteria();
    	uc.createCriteria().andUsernameEqualTo(username);
    	List<User> users = userMapper.selectByExample(uc);
    	return users.get(0);
    }
    
    public int deleteByPrimaryKey(Integer id) {
    	return userMapper.deleteByPrimaryKey(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public int save(User user) {
    	int result;
        if (user.getId() != null) {
//        	result = userMapper.updateByPrimaryKey(user);
        	result = userMapper.updateByPrimaryKeySelective(user);
        	if(user.getPassword().compareTo("111111") == 0) {
        		throw new RuntimeException("roll back");
        	}
        } else {
        	user.setCreatedDate(new Date());
        	user.setUserType((byte)0);
        	user.setStatus((byte)0);
        	user.setOrganizationId(0);
        	result = userMapper.insert(user);
        }
    	return result;
    }
}
