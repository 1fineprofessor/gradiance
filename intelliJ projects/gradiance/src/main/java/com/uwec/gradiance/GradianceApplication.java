package com.uwec.gradiance;

import com.uwec.gradiance.dao.GeneralDao;
import com.uwec.gradiance.database.Users;
import com.uwec.gradiance.model.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@EnableAutoConfiguration  I think this may be redundant because it's implied with springbootapplication
public class GradianceApplication {
	// http://localhost:8085 will let you test when run locally.
	// when run on the server it is just https://gradiance.org
	public static void main(String[] args) {
		SpringApplication.run(GradianceApplication.class, args);
	}


	@Bean
	public CommandLineRunner seedData(GeneralDao dao, PasswordEncoder pw) {
		return args -> {
			// Ensure an admin user exists based on studentId
			String adminStudentId = "admin001";
			if (dao.findByStudentId(adminStudentId).isEmpty()) {
				Users admin = new Users();
				admin.setStudentId(adminStudentId);
				admin.setFirstName("Admin");
				admin.setMiddleInitial("A");
				admin.setLastName("User");
				admin.setEmail("admin@example.com");
				admin.setPassword(pw.encode("adminpass"));
				admin.setRole(RoleEnum.INSTRUCTOR); // Assign admin-like role
				admin.setAdminRights(1);            // Give admin rights

				dao.save(admin);
				System.out.println(">>> Seeded default admin user: admin@example.com / adminpass");
			}
		};
	}

}
