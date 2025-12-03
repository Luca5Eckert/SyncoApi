package com.api.synco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Synco API application.
 *
 * <p>This Spring Boot application provides a RESTful API for academic management,
 * including user authentication, course management, class scheduling, and attendance tracking.</p>
 *
 * <p>The application uses JWT-based authentication and follows a clean architecture pattern
 * with separation between domain, application, and infrastructure layers.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
public class SyncoApiApplication {

	/**
	 * Application entry point.
	 *
	 * <p>Initializes the Spring Boot application context and starts the embedded web server.</p>
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(SyncoApiApplication.class, args);
	}

}
