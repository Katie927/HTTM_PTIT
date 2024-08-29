package httm.dto;

import java.util.regex.Pattern;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRegistration {
	@NotBlank(message = "Email không được để trống")
    @Email(message = "Địa chỉ email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Nhập lại mật khẩu không được để trống")
    private String retypePassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	// Các phương thức kiểm tra tính hợp lệ
//    public boolean isPasswordValid() {
//    	
//        return password.equals(retypePassword);
//    }
	
	// ktra email
	public boolean isEmailValid(String email) {
		String EMAIL_PATTERN = 
		        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		if (email == null || email.isEmpty()) {
			return false;
		}
		return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
	}
	
	// ktra mật khẩu
	public boolean isPasswordValid(String password) {
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
        
        // Kiểm tra nhập lại mk
        if(!password.equals(this.getRetypePassword())) {
        	return false;
        }
        
        // Nếu tất cả các điều kiện đều thỏa mãn, mật khẩu hợp lệ
        return true;
    }
	
}
