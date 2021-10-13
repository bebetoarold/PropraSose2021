package com.teamanime.Propra.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.teamanime.Propra.Services.PersonService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Bean
    public UserDetailsService userDetailsService() {
        return new PersonService();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    
    
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/admins").hasAnyAuthority("ADMIN")
            .antMatchers("/admins/**").hasAnyAuthority("ADMIN")
           // .antMatchers("/statistics").hasAnyAuthority("ADMIN")
            //.antMatchers("/statistics/**").hasAnyAuthority("ADMIN")
            .antMatchers("/roles").hasAnyAuthority("ADMIN")
            .antMatchers("/subjects").hasAnyAuthority("ADMIN")
            .antMatchers("/roles/**").hasAnyAuthority("ADMIN")
            .antMatchers("/subjects/**").hasAnyAuthority("ADMIN")
            .antMatchers("/tutors").hasAnyAuthority("TUTOR","ADMIN","LEARNER")
            .antMatchers("/tutors/list").hasAnyAuthority("TUTOR","ADMIN","LEARNER")
            .antMatchers("/tutors/link").hasAnyAuthority("ADMIN")
            .antMatchers("/sessions").hasAnyAuthority("TUTOR","ADMIN","LEARNER")
            .antMatchers("/sessions/delete").hasAnyAuthority("TUTOR","ADMIN")
            .regexMatchers("/sessions/[macu].{1,}").hasAnyAuthority("TUTOR")
            .regexMatchers("/tutors/[savduf].{1,}").hasAnyAuthority("ADMIN") //supposed to match tutors/find, /tutors/add, /tutors/salary
            .regexMatchers("/learners/[savduf].{1,}").hasAnyAuthority("ADMIN")
            .antMatchers("/payments/bill").hasAnyAuthority("ADMIN")
            .antMatchers("/payments/salary").hasAnyAuthority("ADMIN")
            .regexMatchers("/payments/[f].{1,}").hasAnyAuthority("ADMIN","TUTOR","LEARNER")
            .antMatchers("/payments/singleSalary").hasAnyAuthority("ADMIN","TUTOR","LEARNER")
            .antMatchers("/payments/singleBill").hasAnyAuthority("ADMIN","TUTOR","LEARNER")
            .antMatchers("/tutors/myRoles").hasAnyAuthority("ADMIN")
            .antMatchers("/learners/myRoles").hasAnyAuthority("ADMIN")
            .antMatchers("/tutors/myArea").hasAnyAuthority("TUTOR")
            .antMatchers("/tutors/myArea/**").hasAnyAuthority("TUTOR")
            .antMatchers("/learners/myArea").hasAnyAuthority("LEARNER")
            .antMatchers("/learners/mySessions").hasAnyAuthority("LEARNER")
            .antMatchers("/learners/myArea/**").hasAnyAuthority("LEARNER")
            .antMatchers("/learners/list").hasAnyAuthority("TUTOR","ADMIN","LEARNER")
            .antMatchers("/learners/link").hasAnyAuthority("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
               //.loginPage("/login") /payments/salary
               .defaultSuccessUrl("/")
               .failureUrl("/login?loginError=true")
               .permitAll()
            .and()
            .logout().permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/403")
            .and()
            .sessionManagement()
            .sessionFixation().none();
            ;
    }
}