//package com.JWT.practice;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.JWT.Repository.CustomUserDetailsRepo;
//import com.JWT.entityManager.UserDetailsEntity;
//
//@Component
//public class AdminUserInitializer {
//	@Bean
//	public CommandLineRunner creationAdminUser(CustomUserDetailsRepo customUserDetailsRepo , PasswordEncoder bcryptEncode) {
//		return args -> {;
//			if(customUserDetailsRepo.findByUserName("admin").isEmpty()) {
//				UserDetailsEntity admin = new UserDetailsEntity();	
//				admin.setUserName("admin");
//				admin.setPassword(bcryptEncode.encode("Hello"))	;
//				admin.setRoles("ROLE_ADMIN , ROLE_USER");
//				customUserDetailsRepo.save(admin);
//				System.out.println("Admin User Created");
//			}
//			if(customUserDetailsRepo.findByUserName("user").isEmpty()) {
//				UserDetailsEntity user = new UserDetailsEntity();	
//				user.setUserName("user");
//				user.setPassword(bcryptEncode.encode("Hello"))	;
//				user.setRoles("ROLE_USER");
//				customUserDetailsRepo.save(user);
//				System.out.println("User Created");
//			}
//		};
//		
//	}
//
//}
