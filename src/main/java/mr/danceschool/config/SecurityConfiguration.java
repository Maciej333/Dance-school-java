package mr.danceschool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import mr.danceschool.security.DiplomaAuthenticationFilter;
import mr.danceschool.security.DiplomaAuthorizationFilter;
import mr.danceschool.service.UserService;
import mr.danceschool.utils.Role;
import java.util.Arrays;

@Configuration @EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value( "${diploma.app.jwtSecret}" )
    private String jwtSecret;

    @Value( "${diploma.app.jwtExpirationMs}" )
    private long jwtExpirationDate;

    private UserService userService;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService){
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        DiplomaAuthenticationFilter authFilter = new DiplomaAuthenticationFilter(authenticationManagerBean(), userService, jwtSecret, jwtExpirationDate);
        authFilter.setFilterProcessesUrl("/api/auth/login");

        http.csrf().disable();
        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();

            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.addAllowedOrigin("http://localhost:3000");
            corsConfiguration.setAllowedMethods(Arrays.asList("POST", "OPTIONS", "GET", "DELETE", "PUT"));
            corsConfiguration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
            return corsConfiguration;
        });
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new DiplomaAuthorizationFilter(jwtSecret), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(authFilter);

        http.authorizeRequests().antMatchers("/api/auth/login").permitAll();

        //AnnouncementController
        http.authorizeRequests().antMatchers("/api/announcement/add_school_ann").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/announcement/add_group_ann/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/announcement/get_school_anns").permitAll();
        http.authorizeRequests().antMatchers("/api/announcement/get_group_anns/*").authenticated();
        http.authorizeRequests().antMatchers("/api/announcement/get_ann/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/announcement/update_ann/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/announcement/delete_ann/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());

        //DanceStyleController
        http.authorizeRequests().antMatchers("/api/dance_style/save").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/dance_style/get/*").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/dance_style/update/*").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/dance_style/delete/*").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/dance_style/get_all").permitAll();

        //GroupController
        http.authorizeRequests().antMatchers("/api/group/save_course").hasAnyAuthority(Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/group/save_choreo").hasAnyAuthority(Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/group/get_all").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/group/get_open").permitAll();
        http.authorizeRequests().antMatchers("/api/group/get/*").permitAll();
        http.authorizeRequests().antMatchers("/api/group/update_course/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/group/update_choreo/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/group/delete/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/group/udpate_level/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/group/udpate_status/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/group/udpate_instructors/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/group/group_students/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/group/check_group_user/*").hasAnyAuthority(Role.STUDENT.name());

        //InstructorController
        http.authorizeRequests().antMatchers("/api/instructor/get_groups/*").hasAnyAuthority(Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/instructor/today_groups/*").hasAnyAuthority(Role.INSTRUCTOR.name());

        //LocationController
        http.authorizeRequests().antMatchers("/api/location/save_location").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/location/get_location/*").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/location/get_all_locations").permitAll();
        http.authorizeRequests().antMatchers("/api/location/update_location/*").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/location/delete_location/*").hasAnyAuthority(Role.DIRECTOR.name());

        //StudentController
        http.authorizeRequests().antMatchers("/api/student/get_all").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/student/get_sds/*").authenticated();
        http.authorizeRequests().antMatchers("/api/student/get_groups/*").hasAnyAuthority(Role.STUDENT.name());
        http.authorizeRequests().antMatchers("/api/student/discount/*").hasAnyAuthority(Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/student/pass/*").hasAnyAuthority(Role.STUDENT.name());
        http.authorizeRequests().antMatchers("/api/student/dance_level/*").hasAnyAuthority(Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/student/group/*").hasAnyAuthority(Role.STUDENT.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/student/pass/*").authenticated();
        http.authorizeRequests().antMatchers("/api/student/remove_from_group/*").hasAnyAuthority(Role.STUDENT.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/student/today_groups/*").hasAnyAuthority(Role.STUDENT.name());

        //UserController
        http.authorizeRequests().antMatchers("/api/user/save_student").permitAll();
        http.authorizeRequests().antMatchers("/api/user/save_employee").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/user/get/*").authenticated();
        http.authorizeRequests().antMatchers("/api/user/get_employees").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/user/get_instructors").permitAll();
        http.authorizeRequests().antMatchers("/api/user/update_employee/*").hasAnyAuthority(Role.DIRECTOR.name(), Role.INSTRUCTOR.name());
        http.authorizeRequests().antMatchers("/api/user/update_student/*").hasAnyAuthority(Role.STUDENT.name());
        http.authorizeRequests().antMatchers("/api/user/add_student/*").hasAnyAuthority(Role.INSTRUCTOR.name(), Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/user/delete/*").hasAnyAuthority(Role.DIRECTOR.name());
        http.authorizeRequests().antMatchers("/api/user/refresh").permitAll();
        http.authorizeRequests().antMatchers("/api/user/change_password/*").authenticated();
        http.authorizeRequests().antMatchers("/api/user/delete_employee_student/*").hasAnyAuthority(Role.DIRECTOR.name());

        //InitController
        http.authorizeRequests().antMatchers("/api/init/populate").permitAll();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
