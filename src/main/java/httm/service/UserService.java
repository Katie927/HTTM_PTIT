package httm.service;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import httm.model.User;

@Service
public class UserService extends BaseService<User> {
	
	@Override
	public Class<User> clazz(){
		return User.class;
	}
	
	@Autowired
    private UserRepository userRepository;
	
	public boolean isEmailRegistered(String email) {
        return userRepository.existsByUsername(email);
    }
	
	// tìm người dùng theo mã xác thực
    public User findByEmailVerificationToken(String token) {
        return userRepository.findByEmailVerificationToken(token);
    }
    
    // Phương thức tìm người dùng theo email
    public User findByEmail(String username) {
        return userRepository.findByUsername(username);
    }
}


@Repository
interface UserRepository extends JpaRepository<User, Long> {

	// Tìm kiếm người dùng theo email
	User findByUsername(String username);

	// Kiểm tra email đã tồn tại
	boolean existsByUsername(String username);
	
	// Tìm người dùng theo mã xác thực email
    User findByEmailVerificationToken(String verification_token);
}
