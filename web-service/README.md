# _E-commerce platform_

## Description: 
### This project is a Spring Boot application leveraging Hibernate/JPA for database interaction and PostgreSQL as the database. It's containerized using Docker, with separate containers for PostgreSQL and the web service. The API documentation is provided via Swagger, accessible at http://localhost:8080/swagger-ui/index.html#/.

## Entities:
### Cart: Represents the shopping cart for a user.
### User: Users who can register, login, and create carts.
### Order: Orders placed by users, containing products and user information.
### Product: Items available for purchase, categorized into Gloves, Scarfs, Shoes, T-Shirts, Pants, and Backpacks.

## Flow
### User Registration and Authentication:
- Users register and authenticate using their information.
- Upon successful login, a cart is created for the user.

### Product Management:
- Products are categorized into Gloves, Scarfs, Shoes, T-Shirts, Pants, and Backpacks.
- Users can view products, filter them by category and price, and add/remove them from the cart.

### Cart Operations:
- Users can access their cart information, including the total price of items.

### Order Placement:
- Users can place orders based on the contents of their cart.
- Orders include user information, shipping address, total price, and the products included.

## Technologies Used
- **Spring Boot**: Provides a framework for creating Java applications.
- **Hibernate/JPA**: Enables database interaction through object-relational mapping.
- **PostgreSQL**: Used as the database management system.
- **Docker**: Containerizes the application and its dependencies.
- **Swagger**: Provides API documentation for easy reference and testing.

### Running the Application
- docker-compose build
- docker-compose up

#### Access Swagger Documentation:
Open http://localhost:8080/swagger-ui/index.html#/ in your browser to explore the API endpoints.

### Contributors
- **Adelina Dzhalelova**
- **Asmae Noufoussi**
- **Yifan Hao**
