/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.env.Environment;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 */
@SpringBootApplication
@ImportRuntimeHints(PetClinicRuntimeHints.class)
public class PetClinicApplication {

    private static final Logger log = LoggerFactory.getLogger(PetClinicApplication.class);

    /**
     * Main method to start the Spring Boot application with sensible defaults:
     * - Disables the default Spring banner
     * - Sets a default profile if none provided
     * - Logs application URLs on startup
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(PetClinicApplication.class);
        // Disable the Spring Boot startup banner
        app.setBannerMode(Banner.Mode.OFF);
        // Apply a default profile if no profile is set (e.g., 'dev')
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();

        String protocol = env.getProperty("server.ssl.key-store") != null ? "https" : "http";
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path", "");
        String hostAddress = InetAddress.getLocalHost().getHostAddress();

        log.info("\n----------------------------------------------------------\n" +
                 "\tApplication '{}' is running! Access URLs:\n" +
                 "\tLocal:      {}://localhost:{}{}\n" +
                 "\tExternal:   {}://{}:{}{}\n" +
                 "----------------------------------------------------------",
                 env.getProperty("spring.application.name"),
                 protocol, serverPort, contextPath,
                 protocol, hostAddress, serverPort, contextPath);
    }
}
