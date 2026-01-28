# 🚚 Delivery App – Backend  

## 📌 Project Overview

Delivery App is a Spring Boot backend application that simulates a food delivery platform.  
It provides REST APIs for managing:

- Restaurants
- Menu Items
- Customers
- Orders
- Couriers

The application is fully containerized using Docker 

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- Docker
- Docker Compose
- Maven

---

## 🏗️ Architecture

The application follows a layered architecture:

Controller → Service → Repository → Database

- **Controller** – Handles HTTP requests  
- **Service** – Contains business logic  
- **Repository** – Database access via Spring Data JPA  
- **Entity** – JPA mapped database models  

---

## 📂 Project Structure

```
src/main/java/com/delivery/dvApp
│
├── controller
├── service
├── repository
├── entity
├── enums
└── exception
```

---

## 🗄️ Database

- PostgreSQL
- JPA/Hibernate used for ORM
- Soft delete implemented using `deleted` field

---

## 🔍 Key Features

### 🍽️ Restaurant
- Create restaurant
- Find by name
- Find by category
- Retrieve only non-deleted restaurants

### 🛍️ Items
- Retrieve items by restaurant

### 👤 Customer
- Basic CRUD operations
- View Order History.

### 📦 Orders
- Find orders by:
  - Courier & Status
  - Customer & Status
- Check if courier/customer has active orders
- Create Order 

---

## 🐳 Running with Docker

### 1️⃣ Build the project

```bash
mvn clean package
```

### 2️⃣ Run with Docker Compose

```bash
docker-compose up --build
```

### 3️⃣ Application will be available at:

```
http://localhost:8080
```
OR
if swagger is enabled you can explore the api via swagger.
```
http://localhost:8080/swagger-ui/index.html#/
```

PostgreSQL runs inside a container defined in `docker-compose.yml`.

---

## 📬 Example Endpoints

```
GET    /restaurant/findByName/{name}
POST   /orders/createOrder
GET    /customer/view-activeOrders
GET    /courier/view-courierHistory
```



---

## 📖 API Documentation

All repositories and entities include proper Javadoc documentation.

Spring Data JPA provides built-in CRUD methods such as:

- `save()`
- `findById()`
- `findAll()`
- `delete()`

Custom query methods are implemented using Spring Data method naming conventions.

---

## ⚙️ How to Run Without Docker

### 1. Clone the repository
```bash
git clone https://github.com/Alcom01/deliveryApp-backend.git
cd deliveryApp-backend
```
### :elephant: 2. Configure Database (PostgreSQL)
Update  the `application.properties` file with your local PostgreSQL configuration.
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
### :hammer_and_wrench: 3. Build the Project By Maven
```bash
mvn clean install
```
### :leg: 4. Run the Application
```bash
mvn spring-boot:run
```
The API will be available at: http://localhost:8080

## :microscope:  Running Tests
```bash
mvn test
```

---

## 🧪 Future Improvements

- Add authentication (JWT)
- Add integration tests
- Add pagination & sorting
- Payment Intregration
- Real time location Using WebSockets


---

## 👨‍💻 Author

Alkim Sabancilar 
Student ID=40993

