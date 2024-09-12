package httm.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping(value = "/findEmail", method = RequestMethod.GET)
	public String findEmail() throws IOException {
		return "findEmail";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword() throws IOException{
		return "changePassword";	
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
	
	
	// đăng ký
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute("userRegistration") UserRegistration userRegistration, Model model) {
		
		
		if (!userRegistration.isEmailValid(userRegistration.getEmail())) {
			System.out.println(userRegistration.getEmail());
			System.out.println("sai dinh dang email");
			model.addAttribute("error", "Địa chỉ email không hợp lệ");
            return "signup"; // Quay lại trang đăng ký và hiển thị thông báo lỗi
		} 	
		else if (userService.isEmailRegistered(userRegistration.getEmail())) {
			System.out.println("email da duoc dang ky");
			model.addAttribute("error", "Email đã được đăng ký");
            return "signup"; // Quay lại trang đăng ký và hiển thị thông báo lỗi
        }
		else if (!userRegistration.isPasswordValid(userRegistration.getPassword())) {

			// Xử lý khi mật khẩu không khớp
			// Ví dụ: Hiển thị thông báo lỗi cho người dùng

			System.out.println(userRegistration.getEmail());
			System.out.println(userRegistration.getPassword());
			System.out.println(userRegistration.getRetypePassword());
			model.addAttribute("error", "Mật khẩu không hợp lệ hoặc không khớp");
            return "signup"; // Quay lại trang đăng ký và hiển thị thông báo lỗi
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
//    private String generateToken() {
//        return UUID.randomUUID().toString();
//    }
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private static String generateToken() {
		Random random = new Random();
		StringBuilder token = new StringBuilder(6);

		for (int i = 0; i < 6; i++) {
			token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		}

		return token.toString();
	}
	
    // xác thực email
    @PostMapping("/emailVerify")
    public String verifyEmail(@RequestParam("verification_token") String verificationToken, Model model) {
        User user = userService.findByEmailVerificationToken(verificationToken);

        if (user == null) {
            model.addAttribute("error", "Mã xác thực không hợp lệ.");
            return "emailVerify"; // Trả về trang xác thực với thông báo lỗi
        }

        if (LocalDateTime.now().isAfter(user.getEmailTokenExpiry())) {
            model.addAttribute("error", "Mã xác thực đã hết hạn.");
            return "emailVerify"; // Trả về trang xác thực với thông báo lỗi
        }

        // Nếu mã xác thực hợp lệ và chưa hết hạn, cập nhật trạng thái người dùng
        user.setStatus(true); // Đánh dấu email đã được xác thực
        user.setEmailVerificationToken(null); // Xóa mã xác thực sau khi xác thực thành công
        user.setEmailTokenExpiry(null); // Xóa thời gian hết hạn
        userService.saveOrUpdate(user);

        model.addAttribute("success", "Email của bạn đã được xác thực thành công!");
        return "emailVerify"; // Trả về trang xác thực với thông báo thành công
    }
    
    // xử lý quên mật khẩu
 // Xử lý yêu cầu tìm email để gửi mã đặt lại mật khẩu
    @PostMapping("/findEmail")
    public String findEmail(@RequestParam("username") String email, Model model) {
        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "Email không tồn tại.");
            System.out.println("email khong ton tai");
            return "findEmail"; // Trả về trang tìm email với thông báo lỗi
        }
        
        System.out.println(user.getUsername());
        // Tạo mã xác thực và gửi email
        String resetToken = generateToken();
        user.setEmailVerificationToken(resetToken);
        user.setEmailTokenExpiry(LocalDateTime.now().plusHours(1)); // Token có hiệu lực trong 1 giờ
        userService.saveOrUpdate(user);

        emailService.sendVerificationEmail(user.getUsername(), resetToken);
        
        model.addAttribute("success", "Email đặt lại mật khẩu đã được gửi.");
        return "findEmail"; // Trả về trang tìm email với thông báo thành công
    }
    
 // Xử lý yêu cầu đặt lại mật khẩu
    @PostMapping("/resetPassword")
    public String resetPassword( @RequestParam("token") String token, @RequestParam("newPassword") String newPassword, Model model) {
        User user = userService.findByEmailVerificationToken(token);

        if (user == null || LocalDateTime.now().isAfter(user.getEmailTokenExpiry())) {
            model.addAttribute("error", "Mã xác thực không hợp lệ hoặc đã hết hạn.");
            return "resetPassword"; // Trả về trang đặt lại mật khẩu với thông báo lỗi
        }

        // Cập nhật mật khẩu mới
        if(!isPasswordValid(newPassword)) {
        	System.out.println();
        	model.addAttribute("error", "Mật khẩu không hợp lệ.");
            return "findEmail";
        }
        user.setPassword(new BCryptPasswordEncoder(4).encode(newPassword));
        user.setEmailVerificationToken(null); // Xóa mã xác thực sau khi thay đổi mật khẩu
        user.setEmailTokenExpiry(null); // Xóa thời gian hết hạn
        userService.saveOrUpdate(user);

        model.addAttribute("success", "Mật khẩu của bạn đã được thay đổi thành công.");
        return "findEmail"; // Trả về trang đặt lại mật khẩu với thông báo thành công
    }
    
    // doi mat khau
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("password") String password, @RequestParam("email") String email,
    												@RequestParam("newPassword") String newPassword,
    												@RequestParam("retypePassword") String retypePassword, Model model) {
    	
    	System.out.println(email);
        User user = userService.findByEmail(email);
        
        // kiểm tra mật khẩu hiện tại
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        if(!encoder.matches(password, user.getPassword())) {
        	System.out.println("Mật khẩu hiện tại không chính xác!");
        	System.out.println(new BCryptPasswordEncoder(4).encode(password));
        	System.out.println(user.getPassword());
        	model.addAttribute("error", "Mật khẩu hiện tại không chính xác!");
        	return "changePassword";
        }
        
        // Cập nhật mật khẩu mới
        if(!isPasswordValid(newPassword)) {
        	System.out.println();
        	model.addAttribute("error", "Mật khẩu không hợp lệ.");
            return "changePassword";
        }
        
        if(!newPassword.equals(retypePassword)) {
        	System.out.println(retypePassword);
        	System.out.println(newPassword);
        	System.out.println("Mật khẩu mới không đúng!");
        	model.addAttribute("error", "Nhập lại mật khẩu không đúng!");
        	return "changePassword";
        }
        
        user.setPassword(new BCryptPasswordEncoder(4).encode(newPassword));
        userService.saveOrUpdate(user);

        model.addAttribute("success", "Mật khẩu của bạn đã được thay đổi thành công.");
        return "changePassword"; // Trả về trang đặt lại mật khẩu với thông báo thành công
    }
    
    // kiemm tra mat khau
    private boolean isPasswordValid(String password) {
        // Định nghĩa các mẫu regex cho từng yêu cầu
        String uppercasePattern = ".*[A-Z].*";
        String lowercasePattern = ".*[a-z].*";
        String digitPattern = ".*\\d.*";
        String specialCharPattern = ".*[^a-zA-Z0-9].*";
        
        // Kiểm tra mật khẩu phải có ít nhất 8 ký tự
        if (password.length() < 8) {
            return false;
        }
        
        // Kiểm tra mật khẩu có chứa ít nhất một chữ cái viết hoa
        if (!Pattern.matches(uppercasePattern, password)) {
            return false;
        }
        
        // Kiểm tra mật khẩu có chứa ít nhất một chữ cái viết thường
        if (!Pattern.matches(lowercasePattern, password)) {
            return false;
        }
        
        // Kiểm tra mật khẩu có chứa ít nhất một chữ số
        if (!Pattern.matches(digitPattern, password)) {
            return false;
        }
        
        // Kiểm tra mật khẩu có chứa ít nhất một ký tự đặc biệt
        if (!Pattern.matches(specialCharPattern, password)) {
            return false;
        }
        
        // Nếu tất cả các điều kiện đều thỏa mãn, mật khẩu hợp lệ
        return true;
    }
}
