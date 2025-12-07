# VeriVue - News Reading and Management System (Backend)

**VeriVue** is a **News Reading and Management System Backend** developed using **Java**, **Spring Boot**, and **Spring Cloud**.

It provides three sets of APIs for different user roles:

- **Admin APIs**: Enable administrators to manage channels, sensitive words, user verification, and perform manual article reviews.

- **WeMedia APIs**: Enable content creators to manage their articles, media materials, comments, and view article statistics.

- **User APIs**: Allow end users to browse and search articles, interact with content, and submit real-name verification requests to create a WeMedia account.



---



## Table of Contents

- [Features](#features)  
- [Tech Stack](#tech-stack)  
- [Project Setup & Run](#project-setup--run)  



---



## Features

### 1. Admin APIs

| Module                           | Description                                                  |
| -------------------------------- | ------------------------------------------------------------ |
| Login/Logout                     | Admin authentication to access backend APIs.                 |
| Admin Management                 | Add, retrieve, and update admin accounts.                    |
| Channel Management               | Create, update, retrieve, and delete news channels.          |
| Sensitive Word Management        | Manage sensitive words, including add, update, retrieve, and delete. |
| User Verification Management     | Review,  approve or reject real-name verification requests. Upon approval, the system automatically creates a WeMedia account and sends an email notification to user. |
| Article Manual Review Management | Retrieve submitted articles and their details. Articles containing sensitive words may be flagged for manual review by admins. |



### 2. WeMedia APIs

| Module                | Description                                                  |
| --------------------- | ------------------------------------------------------------ |
| Login/Logout          | Creator authentication to access WeMedia APIs.               |
| User Management       | Retrieve and update creator profile information.             |
| Statistics Management | View article-level statistics and performance metrics.       |
| Material Management   | Add, retrieve, delete, favorite, or unfavorite media materials. |
| Article Management    | Retrieve all articles, create, update, delete, publish or unpublish articles, and view article details. |
| Comment Management    | Retrieve article comments, set comment status, reply to comments, like comments, and delete comments. |



### 3. User APIs

| Module                 | Description                                                  |
| ---------------------- | ------------------------------------------------------------ |
| Login/Logout           | User authentication to access User APIs.                     |
| User Management        | Add, update, and retrieve user information.                  |
| Article Management     | Browse and view articles.                                    |
| Behavior Management    | User interactions with articles: like, dislike, view count, follow or unfollow authors, collect articles, and display all behaviors. |
| Comment Management     | View, post, like, and reply to comments.                     |
| Search Management      | Search articles, retrieve and delete search history, and get related search suggestions. |
| Real-name Verification | Submit real-name verification requests for creating WeMedia accounts. |



---



## Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 2.7.18
- **Data Access**: MyBatisPlus
- **Database**: MySQL, MongoDB
- **Cache Storage**: Redis
- **Object Storage**: MinIO
- **Search & Messaging**: Elasticsearch, Kafka
- **Security**: JWT Authentication
- **Service Discovery & Config**: Spring Cloud, Nacos
- **Build Tools**: Maven, Lombok
- **Task Scheduling**: XXL-Job 2.3.0



---



## Project Setup & Run

### 1. Prerequisite

Before running the project, ensure you have the following software installed (either via Docker or locally):

| Service       | Version                      |
| ------------- | ---------------------------- |
| Java          | 17                           |
| Maven         | 3.6+                         |
| MySQL         | 8.x                          |
| Nacos         | 2.2.0                        |
| Redis         | 5.0.5                        |
| MinIO         | RELEASE.2021-04-06T23-11-00Z |
| MongoDB       | 4.2.1                        |
| Kafka         | 2.12-2.3.1                   |
| Zookeeper     | 3.4.14                       |
| Elasticsearch | 7.4.0                        |
| XXL-Job       | 2.3.0                        |

> ⚠️ Make sure all services are running and accessible by the application before starting the backend.



### 2. Download or Clone the Repository

```
git clone https://github.com/WenHuey04/VeriVue.git
cd VeriVue
```

### 3. Configure MySQL Database

 1. **Create databases manually** before importing SQL:

    ```sql
    # Example
    	
    -- MySQL
    CREATE DATABASE verivue_admin CHARACTER SET utf8mb4 COLLATE 	utf8mb4_unicode_ci;	
    
    -- MongoDB
    use verivue-comment
    ```

2. Import provided SQL files from the `source/mysql/` and  `source/mongoDB/`folder.

> ⚠️ SQL files do **not** include database creation. Ensure databases exist before import.

### 4. Configure Nacos

1. Start Nacos.
2. Import provided Nacos configuration files.
3. Edit **all the `bootstrap.yml` files,** replacing all **`#`** placeholders with your actual values:

```
# Example
spring:
  cloud:
    nacos:
      discovery:
        server-addr: # your Nacos server address
      config:
        server-addr: # your Nacos server address
```

> ⚠️ All placeholders must be replaced for proper connection.

### 5. Configure Other Services

Update **all** the **Nacos configuration files** and **local `bootstrap.yml` files** with actual host/port/access info for:

- Redis
- MinIO
- Kafka
- Elasticsearch
- Email 

### 6. Build & Run the Backend

```
mvn clean install
mvn spring-boot:run
```

### 7. API Testing

- Use the provided **Postman collection** to test all APIs. 
- Postman includes sample accounts, request bodies, and headers for convenience.

> ⚠️ **Disclaimer:** All APIs in the provided Postman collection are implemented and functional. However, the **description and documentation** within the collection are automatically generated by AI and may not be fully accurate. Please rely on the actual API behavior and verify request/response details when testing.


#### Reference

For more details about XXL-Job, please check the official repository: 
👉 [https://github.com/xuxueli/xxl-job](https://github.com/xuxueli/xxl-job)

> ℹ️ Note: This project only uses **xxl-job-admin** for scheduled task management.  
>
> The `xxl-job-core` and `xxl-job-executor-samples` modules are **not included**, as they are unnecessary for this use case.

