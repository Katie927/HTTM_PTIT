package httm.configurer;

import java.io.IOException;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// chuyen den request phu hop theo role
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	// dieu huong request
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	// chuyen den request phu hop theo role khi xac thuc thanh cong
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
											Authentication authentication) throws IOException, ServletException {
		
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		
	}

	// dua vao role cua user de xac dinh request
	protected void handle(HttpServletRequest request, HttpServletResponse response, 
							Authentication authentication) throws IOException {
		
		String targetUrl = "/changePassword";	// lay url theo role user
		if(response.isCommitted()) {
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(final Authentication authentication) throws IllegalStateException {
		
//		Map<String, String> roleTargetUrlMap = new HashMap<String, String>();
//		// key la role name, value la url (action)
//		roleTargetUrlMap.put("ADMIN", "/admin/index");
//		roleTargetUrlMap.put("GUEST", "/index");
//		
//		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();	
//																// lay danh sach cac role	
//		for(final GrantedAuthority grantedAuthority:authorities) {
//			// authorities lay trong class user
//			String authorityName = grantedAuthority.getAuthority();
//			// role name
//			
//			if(roleTargetUrlMap.containsKey(authorityName)) {
//				return roleTargetUrlMap.get(authorityName);
//			}
//		}
//		
//		throw new IllegalStateException();
		return "/login";
	}
	
	
}
