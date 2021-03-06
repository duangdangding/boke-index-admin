package com.pearadmin.secure;

import javax.annotation.Resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.pearadmin.common.config.proprety.SecurityProperty;
import com.pearadmin.secure.process.SecureAccessDeniedHandler;
import com.pearadmin.secure.process.SecureAuthenticationEntryPoint;
import com.pearadmin.secure.process.SecureAuthenticationFailureHandler;
import com.pearadmin.secure.process.SecureAuthenticationSuccessHandler;
import com.pearadmin.secure.process.SecureLogoutHandler;
import com.pearadmin.secure.process.SecureLogoutSuccessHandler;
import com.pearadmin.secure.process.SecureRememberMeHandler;
import com.pearadmin.secure.process.SecureSessionExpiredHandler;
import com.pearadmin.secure.support.SecureCaptchaSupport;
import com.pearadmin.secure.support.SecurePermissionSupport;

/**
 * Describe: Security ????????????
 * Author: ????????????
 * CreateTime: 2019/10/23
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperty.class)
public class SecureConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * ?????????????????????????????????
     */
    @Resource
    private SecureAuthenticationEntryPoint securityAuthenticationEntryPoint;

    /**
     * ?????????????????????
     */
    @Resource
    private SecureAuthenticationSuccessHandler securityAccessSuccessHandler;

    /**
     * ?????????????????????
     */
    @Resource
    private SecureAuthenticationFailureHandler securityAccessFailureHandler;

    /**
     * ?????????????????????
     */
    @Resource
    private SecureLogoutSuccessHandler securityAccessLogoutHandler;

    /**
     * ?????????????????????
     */
    @Resource
    private SecureAccessDeniedHandler securityAccessDeniedHandler;

    /**
     * ???????????????url
     */
    @Resource
    private SecurityProperty securityProperty;

    /**
     * ??????userservice
     */
    @Resource
    private UserDetailsService securityUserDetailsService;
    // private ISysUserService securityUserDetailsService;

    /**
     * remember me redis?????????
     */
    @Resource
    private PersistentTokenRepository securityUserTokenService;

    /**
     * ????????????????????????
     */
    @Resource
    private SecureCaptchaSupport securityCaptchaSupport;
    @Resource
    private SecurePermissionSupport securePermissionSupport;

    @Resource
    private SecureSessionExpiredHandler securityExpiredSessionHandler;

    /**
     * ????????????
     */
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * ????????????????????????
     */
    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private SecureLogoutHandler securityLogoutHandler;

    @Resource
    private SecureRememberMeHandler rememberMeAuthenticationSuccessHandler;


    /**
     * ??????????????????
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * Describe: ?????? Security ????????????
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(securityProperty.getOpenApi()).permitAll()
                // ????????????????????????????????????
                .anyRequest().authenticated()
                .and()
                // ??????????????????
                .addFilterBefore(securityCaptchaSupport, UsernamePasswordAuthenticationFilter.class)
                .httpBasic()
                .authenticationEntryPoint(securityAuthenticationEntryPoint)
                .and()
                .formLogin()
                // ????????????
                .loginPage("/login")
                // ????????????
                .loginProcessingUrl("/login")
                // ????????????????????????????????????
                .successHandler(securityAccessSuccessHandler)
                // ????????????????????????????????????
                .failureHandler(securityAccessFailureHandler)
                .and()
                .logout()
                .addLogoutHandler(securityLogoutHandler)
                // ?????????????????? cookie??????
                .deleteCookies("JSESSIONID")
                // ????????????????????????????????????
                .logoutSuccessHandler(securityAccessLogoutHandler)
                .and()
                .exceptionHandling()
                // ????????????????????????????????????
                .accessDeniedHandler(securityAccessDeniedHandler)
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("rememberme-token")
                .authenticationSuccessHandler(rememberMeAuthenticationSuccessHandler)
                .tokenRepository(securityUserTokenService)
                .key(securityProperty.getRememberKey())
                .userDetailsService(securityUserDetailsService)
                .and()
                .sessionManagement()
                .sessionFixation()
                .migrateSession()
                // ??????????????????session????????????session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // ?????????????????????????????????
                .maximumSessions(securityProperty.getMaximum())
                .maxSessionsPreventsLogin(false)
                // ??????????????????
                .expiredSessionStrategy(securityExpiredSessionHandler)
                // ??????????????????
                .sessionRegistry(sessionRegistry);
        // ??????permission
        http.authorizeRequests().expressionHandler(defaultWebSecurityExpressionHandler());
        // ??????????????????????????????
        http.csrf().disable();
        // ??????iframe ????????????
        http.headers().frameOptions().disable();
    }
    
    @Bean
    public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setPermissionEvaluator(securePermissionSupport);
        return defaultWebSecurityExpressionHandler;
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
}
