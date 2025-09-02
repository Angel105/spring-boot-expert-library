# Spring Boot Expert Library

![License](https://img.shields.io/badge/License-Proprietary-red.svg)
![Java](https://img.shields.io/badge/Java-18-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-green.svg)

## Overview
Spring Boot Expert Library is a secure, modern backend application built with Spring Boot, designed to serve a React-based library system. This application implements secure authentication, payment processing, and database operations.

## Technologies
- **Java Version**: 18
- **Spring Boot**: 2.7.18
- **Key Dependencies**:
  - Spring Data JPA
  - Spring Data REST
  - Spring Web
  - Spring Boot Actuator
  - Okta Spring Boot Starter (Authentication)
  - Stripe Java (Payment Processing)
  - MySQL Connector
  - Lombok
  - Spring dotenv

## Security Features
- HTTPS-only communication
- Okta OAuth2 integration for secure authentication
- SSL/TLS support with PKCS12 keystore

## Prerequisites
- Java 18 or higher
- MySQL database
- Maven
- SSL certificate (PKCS12 format)

## Configuration
The application uses environment variables for configuration. Create a `.env` file in the root directory with the following variables:

# Database Configuration
properties DATASOURCE_URL=your_database_url DATASOURCE_USERNAME=your_database_username DATASOURCE_PASSWORD=your_database_password

# Okta Configuration
properties OKTA_OAUTH2_CLIENT_ID=your_okta_client_id OKTA_OAUTH2_ISSUER=your_okta_issuer_url

# SSL Configuration
properties SERVER_PORT=8443 
SSL_KEY_ALIAS=your_ssl_key_alias 
SSL_KEY_STORE_PATH=path_to_your_keystore 
SSL_KEY_STORE_PASSWORD=your_keystore_password

# Payment Processing
properties STRIPE_SECRET_KEY=your_stripe_secret_key

## Deployment
1. Ensure all prerequisites are installed
2. Configure your environment variables
3. Set up your SSL certificate
4. Configure your database
5. Set up Okta authentication
6. Configure Stripe payment processing
7. Build and deploy the application

For detailed deployment instructions, please refer to our deployment guide.

## Building the Application
bash mvn clean install


## Running the Application
bash mvn spring-boot:run

The application will start on port 8443 (HTTPS) by default.

## Features
- Secure HTTPS communication
- OAuth2 authentication with Okta
- Payment processing integration with Stripe
- RESTful API endpoints
- Database operations with JPA
- Health monitoring with Spring Actuator

## API Documentation
The API documentation is available through Spring Data REST. Once the application is running, you can access it at:
[https://localhost:8443/api](https://localhost:8443/api)


## Contributing
Please read the contributing guidelines before submitting any pull requests.

## Security Notice
This software implements several security measures:
- End-to-end HTTPS encryption
- OAuth2 authentication through Okta
- Secure payment processing via Stripe
- Environment-based configuration management
- Cross-Origin Resource Sharing (CORS) protection

⚠️ **Security Warning**: Never commit the `.env` file or any sensitive credentials to version control.

## License
Proprietary Software with Limited License

Copyright (c) 2024-2025 DAP-Studio. All rights reserved.

This software is protected by copyright laws and international treaties. This software is licensed, 
not sold, and is subject to a valid commercial license agreement from DAP-Studio.

### Terms of Use:
1. **Commercial License Required**: Any use, reproduction, modification, or distribution of this 
   software requires a valid commercial license from DAP-Studio.
   
2. **No Unauthorized Use**: Unauthorized copying, modification, distribution, or use of this 
   software is strictly prohibited and may result in legal action.

3. **Intellectual Property**: All intellectual property rights in the Software, including 
   but not limited to copyrights, patents, trade secrets, and trademarks, are owned by DAP-Studio.

4. **No Warranty**: THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
   IMPLIED. DAP-STUDIO MAKES NO WARRANTIES OR REPRESENTATIONS REGARDING THE USE OR PERFORMANCE 
   OF THE SOFTWARE.

For licensing inquiries and pricing:
- Email: []
- Website: []

# Support
For support and questions, please open an issue in the repository.

---
**Note**: Make sure to never commit sensitive information such as API keys, passwords, or certificates to version control.








