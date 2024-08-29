package httm.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import httm.dto.UserRegistration;
import httm.model.User;
import httm.service.EmailService;
import httm.service.UserService;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() throws IOException {
		return "login";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() throws IOException {
		return "signup";
	}
	
	@RequestMapping(value = "/forgetpassword", method = RequestMethod.GET)
	public String forgetPassword() throws IOException {
		return "forgetPassword";
	}
	
	@RequestMapping(value = "/emailverify", method = RequestMethod.GET)
	public String emailVerify() throws IOException {
		return "emailVerify";
	}
	
//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public String register(final Model model, final HttpServletRequest request,
//											final HttpServletResponse response) throws IOException {
//		
//		if (userRegistration.isPasswordValid()) {
//	        User user = new User();
//	        user.setUsername(userRegistration.getEmail());
//	        user.setPassword(new BCryptPasswordEncoder(4).encode(userRegistration.getPassword()));
//	        userRepository.save(user);
//	        return "redirect:/login";
//	    } else {
//	        // Xử lý khi mật khẩu không khớp
//	        // Ví dụ: Hiển thị thông báo lỗi cho người dùng
//	        return "error-page";
//	    }
//		
////		User user = new User();
////		user.setUsername(request.getParameter("username"));
////		user.setPassword(new BCryptPasswordEncoder(4).encode(request.getParameter("password")));
////		
////		userService.saveOrUpdate(user);
////		return "redirect:/login";
//	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute("userRegistration") UserRegistration userRegistration) {
		if (!userRegistration.isEmailValid(userRegistration.getEmail())) {
			System.out.println(userRegistration.getEmail());
			System.out.println("sai dinh dang email");
			return "redirect:/forgetpassword";
		} else if (!userRegistration.isPasswordValid(userRegistration.getPassword())) {

			// Xử lý khi mật khẩu không khớp
			// Ví dụ: Hiển thị thông báo lỗi cho người dùng

			System.out.println(userRegistration.getEmail());
			System.out.println(userRegistration.getPassword());
			System.out.println(userRegistration.getRetypePassword());
			System.out.println("password");
			return "redirect:/forgetpassword";
		} else {
			User user = new User();
			user.setUsername(userRegistration.getEmail());
			user.setPassword(new BCryptPasswordEncoder(4).encode(userRegistration.getPassword()));
			user.setStatus(false);	// đánh dấu email chưa xác thực
			userService.saveOrUpdate(user);
			
			// Tạo mã xác thực và gửi email
            String verificationToken = generateToken();
            user.setEmailVerificationToken(verificationToken);
            user.setEmailTokenExpiry(LocalDateTime.now().plusHours(1)); // Token có hiệu lực trong 1 giờ
            userService.saveOrUpdate(user);

            emailService.sendVerificationEmail(user.getUsername(), verificationToken);

			System.out.println(userRegistration.getEmail());
			System.out.println(userRegistration.getPassword());
			System.out.println(userRegistration.getRetypePassword());
			System.out.println("Thanh cong");

			return "redirect:/emailverify";
		}
	}
	
	// Phương thức tạo mã xác thực
    private String generateToken() {
        return UUID.randomUUID().toString();
    }
	
}
