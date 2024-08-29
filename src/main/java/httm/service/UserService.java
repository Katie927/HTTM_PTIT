package httm.service;

import org.springframework.stereotype.Service;

import httm.model.User;

@Service
public class UserService extends BaseService<User> {
	
	@Override
	public Class<User> clazz(){
		return User.class;
	}
	
}
