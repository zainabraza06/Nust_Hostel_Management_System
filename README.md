
# 🏠 Hostel Management System (HMS)

A full-stack web application developed to streamline hostel operations like mess off requests, leave requests, and room cleaning management for internal hostelites at NUST.

---

## 🚀 Features

### 🧑‍🎓 Hostelite
- Submit **mess off** requests (limited to 2–12 days/month)
- Submit **leave** requests
- Request **room cleaning**
- View all previous requests and track:
  - Mess off days used
  - Leave history
  - Last cleaning status

### 🧑‍💼 Manager
- View and **approve/reject mess and leave requests** with remarks
- **Assign cleaning staff** to room cleaning requests
- Monitor request traffic and handle hostel operations efficiently

### 🧹 Cleaning Staff
- View today's assigned **cleaning tasks**
- **Mark tasks as completed**

---

## 🛠️ Tech Stack

| Layer       | Technology              |
|-------------|--------------------------|
| Backend     | Java, Spring Boot, Spring Security |
| Database    | MySQL, Hibernate, JPA    |
| Frontend    | HTML, CSS, JavaScript    |
| Tools       | Maven, Git, Postman      |

---

## 📂 Project Structure

```

HMS/
├── src/
│   ├── main/
│   │   ├── java/com/hostelmanagement/
│   │   │   ├── controller/       # Request handling (Hostelite, Manager, Staff)
│   │   │   ├── service/          # Business logic (mess off validation, assignment)
│   │   │   ├── repository/       # JPA interfaces
│   │   │   ├── model/ (entity/)  # JPA entities (User, Request, Staff)
│   │   │   └── security/         # Spring Security config
│   │   └── resources/
│   │       ├── templates/        # HTML pages
│   │       ├── static/           # CSS, JS
│   │       └── application.properties # DB config
├── pom.xml                        # Project dependencies

````

---

## 🧪 Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/zainabraza06/Nust_Hostel_Management_System.git
   cd hostel-management-system
````

2. **Configure MySQL Database**

   * Create a database named `hms_db`
   * Update `src/main/resources/application.properties` with your DB credentials

3. **Run the Project**

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the App**

   * Visit `http://localhost:8080`
   * Use seeded users 

---

## 📚 Learning Outcomes

* Role-based access with Spring Security
* Enforcing real-world business logic in service layer
* Clean MVC architecture in Spring Boot
* Seamless front-back integration
* Team collaboration using Git and GitHub

---

## ⚠️ Known Issues / Future Improvements

* Add notifications for request status changes
* Auto-bill generation
* Adding other types of requests
* Implement analytics dashboard for manager

