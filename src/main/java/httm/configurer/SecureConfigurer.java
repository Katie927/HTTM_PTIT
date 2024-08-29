package httm.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import httm.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecureConfigurer extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
		// bat dau cau hinh
		http.csrf().disable().authorizeRequests()		// bat cac request tu browser
		
		// cho phep cac request, static resource khong bi rang buoc login
		.antMatchers("/frontend/**", "/backend/**", "/FileUploads/**", "/login", "/logout").permitAll()
		// cac request admin can xac thuc
		//.antMatchers("/admin/**").authenticated()	// step 1+2
		// request admin can role ADMIN
		
//		.antMatchers("/admin/**").hasAuthority("ADMIN")
		
		.and()
		
		// neu chua login thi redirect den trang login (/login la 1 request)
		.formLogin().loginPage("/login").loginProcessingUrl("/login_processing_url")
		
		//.defaultSuccessUrl("/admin/index", true)
		// login thanh cong - chuyen den request phu hop
		.successHandler(new UrlAuthenticationSuccessHandler())
		// login khong thanh cong
		.failureUrl("/login?login_error=true")
		
		.and()
		
		// cau hinh phan logout
		.logout().logoutUrl("/logout").logoutSuccessUrl("/login")
			.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		
		.and()
		.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400);
		
	}
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception	{
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(4));
	}
	
//	public static void main(String [] agrs) {
//		System.out.println(new BCryptPasswordEncoder(4).encode("123"));
//	}
	
}
