package httm.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import httm.model.User;

@Controller
public class BaseController {
	
	@ModelAttribute(value = "title")
	public String projectTitle() {
		return "httm";
	}
	
	

	// lay thong tin user dang nhap
	@ModelAttribute("loginedUser")
	public User getLoginedUser() {

		Object loginedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (loginedUser != null && loginedUser instanceof UserDetails) {
			User user = (User) loginedUser;
			return user;
		}
		return new User();
	}

	// kiem tra da login hay chua
	@ModelAttribute
	public boolean isLogined() {
		Object loginedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (loginedUser != null && loginedUser instanceof UserDetails) {
			return true;
		}
		return false;
	}
	
}
