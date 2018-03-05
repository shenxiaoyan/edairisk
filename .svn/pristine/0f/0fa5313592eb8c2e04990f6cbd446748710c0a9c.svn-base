package com.liyang.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.liyang.controller.GlobalAccessDeniedHandler;
import com.liyang.domain.user.UserRepository;
//import com.liyang.filter.MessageDispatchFilter;
import com.liyang.filter.WechatAuthenticationFilter;
import com.liyang.service.UserService;
import com.liyang.util.MD5Util;


@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public WechatAuthenticationFilter wechatAuthenticationFilter() {
		WechatAuthenticationFilter upaf = new WechatAuthenticationFilter();
		upaf.setAuthenticationManager( new AuthenticationManager() {

			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {

				return authentication;
			}
		});
		return upaf;
	}

	
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
		
	}

	/**
	 * 主过滤器
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterAfter(wechatAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.headers().frameOptions().disable();
		http
		.csrf().disable().anonymous().authorities("ANONYMOUS")
		.and()
		.authorizeRequests().antMatchers("/wechat/wlqz/**","/wechat/ddcf/**","/html/**","/js/**","/css/**","/img/**","/images/**","/*.txt","/*.apk","/beidou","/employeeApply","/smsCode","/weblogin","/register","/test","/resetPassword","/beidou/wechatSign")
		.permitAll()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.GET,"/rest/*States","/rest/*Acts","/rest/*Workflows","/rest/feetypes","/rest/departmenttypes","/rest/productstorefees","/rest/productcompanyfees","/rest/creditrepayments","/rest/banks","/rest/loans","/rest/products")
		.authenticated()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST,"/rest/*")
		.authenticated()
		.and()
		.authorizeRequests().antMatchers("/rest","/rest/*","/rest/*/","/rest/*States","/rest/*Acts","/rest/*Logs","/rest/*Files","/rest/*Workflows")
		.hasAuthority("ADMINISTRATOR")
		.and().authorizeRequests().anyRequest().authenticated()   //拦截
		.and()
		.exceptionHandling().accessDeniedHandler(new GlobalAccessDeniedHandler())
		.and()
		.authorizeRequests().antMatchers( "/wechatLogin", "/login").permitAll()
		.and()
		.formLogin().defaultSuccessUrl("/").loginPage("/login").permitAll()
		.and()
		.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 30)
		.and()
		.logout().permitAll();

	}
	
	@Bean
    UserDetailsService customUserService() {
        return new UserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder(){
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String)rawPassword));
            }});
    }

}
