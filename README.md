# 📝 Exam Management System 🧠

A **Java-based Exam Management System** built using **NetBeans IDE** and **MySQL**, designed to simplify and automate exam-related tasks for educational institutions.
This project is part of the **BCA Final Year Project** and includes the **complete ER diagram**, **database structure**, and a **detailed project report**.

---

## 📌 Project Overview

The **Exam Management System** provides a secure and user-friendly platform where:

* 🧑‍💼 **Admins** can manage exams, questions, and student records.
* 🧑‍🎓 **Students** can register, log in, attempt exams, and view their results in real time.

The project uses **Java Swing** for the GUI and **MySQL** for data storage, ensuring fast and secure performance.

---

## ✨ Features

### 🧑‍💼 Admin Panel

* Add / Update / Delete Questions
* Manage Students & Results
* Schedule Exams

### 🧑‍🎓 Student Module

* Register & Login
* Attempt MCQ-Based Exams
* View Scores Instantly

### 🧠 Question Management System

* 🔐 Secure Database Integration (MySQL + JDBC)
* 📝 Real-time Result Generation
* 📊 ER & DFD Diagrams + Full Project Report

---

## 🛠️ Tech Stack

| Component        | Technology            |
| ---------------- | --------------------- |
| 💻 Frontend      | Java Swing (NetBeans) |
| 🗄️ Backend      | MySQL                 |
| 🧠 IDE           | NetBeans              |
| 📝 Language      | Java (JDK 8+)         |
| 🔐 DB Connection | JDBC                  |

---

## 📂 Project Structure

```
Exam-Management-System/
├─ nbproject/
├─ src/
│  └─ exammanagement/       # Java source files (Admin & Student Modules)
├─ dist/
├─ database/
│  └─ exam_management.sql
├─ report/
│  ├─ Project-Report.pdf
│  └─ ER-Diagram.pdf
├─ screenshots/
├─ README.md
└─ .gitignore
```

---

## 📊 ER Diagram

📌 **[View ER Diagram (PDF)](./report/ER-Diagram.pdf)**
(Opens directly in GitHub's PDF viewer)

---

## 📘 Project Report

📄 **[Download Full Project Report (PDF)](./Exam%20Management%20Systemn%20Project%20Report.pdf)**

The report includes:

* Problem Definition
* Objective
* Existing & Proposed System
* System Design (DFD, ERD, Schema)
* Implementation Details
* Output Screenshots
* Conclusion

---

## 🧰 How to Run Locally

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/rekibur-uddin/Exam-Management-System.git
cd Exam-Management-System
```

### 2️⃣ Import into NetBeans

* Open **NetBeans IDE**
* Go to `File → Open Project`
* Select the project folder

### 3️⃣ Import the Database

* Open **phpMyAdmin** or **MySQL Workbench**
* Create a new schema (e.g., `exam_management`)
* Import `database/exam_management.sql`

### 4️⃣ Update DB Credentials

In the source code, update the **JDBC URL**, **username**, and **password** to match your local MySQL configuration.

### 5️⃣ Run the Project

* Click **▶️ Run Project** in NetBeans

---

## 🖼️ Screenshots

| Login Page                        | Student Exam Interface          |
| --------------------------------- | ------------------------------- |
| <img width="780" height="663" alt="Screenshot 2025-10-20 102907" src="https://github.com/user-attachments/assets/bee725e5-d141-4156-b949-3bfaafce7c60" /> |    <img width="809" height="705" alt="Screenshot 2025-10-20 102929" src="https://github.com/user-attachments/assets/b9e94469-d01c-48be-bee4-4836157602b2" />|

| Admin Dashboard                   | Question Management                       |
| --------------------------------- | ----------------------------------------- |
| <img width="742" height="524" alt="Screenshot 2025-10-20 103011" src="https://github.com/user-attachments/assets/d5f94850-90b3-436f-9b5d-7fd537250635" />|    <img width="733" height="480" alt="Screenshot 2025-10-20 103021" src="https://github.com/user-attachments/assets/e14b0bc5-e559-4fb3-9753-8075ab6466f4" /> |

| Exam History                         | Student Registration                    |
| ----------------------------------- | --------------------------------------- |
|       <img width="724" height="505" alt="Screenshot 2025-10-20 103030" src="https://github.com/user-attachments/assets/fdac6137-ccae-45fa-9f0e-041f93ddb993" />  | <img width="1097" height="736" alt="Screenshot 2025-10-20 102853" src="https://github.com/user-attachments/assets/7e453ffb-0599-41ee-9950-fdf4d32b20f7" />  |

📸 **[View All Screenshots in PDF]**

---

## ✍️ Author

👤 **Rekibur Uddin**
📧 [Visit My Portfolio](https://rekiburuddin.blogspot.com)

---

## ⭐ Support

If you like this project, consider giving it a ⭐ on [GitHub](https://github.com/rekibur-uddin/Exam-Management-System/) 🙌
