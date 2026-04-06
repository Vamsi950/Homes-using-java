# Real Estate Property Listing System (Full-Stack)

A modern, full-stack real estate platform that allows users to browse property listings, view detailed information, and contact sellers. The system includes an administrative interface for managing properties and secure authentication for users and admins.

## 🏗️ Project Architecture
The project is built using a decoupled **Client-Server architecture**:
- **Frontend**: A responsive Single Page Application (SPA) built with **React**.
- **Backend**: A robust REST API built with **Spring Boot** (Java).
- **Database**: An embedded **H2 Relational Database** for fast and efficient data management.

---

## 🚀 Features

### 💻 Frontend (React + Vite)
- **Dynamic Property Browsing**: High-quality property cards displaying price, location, beds, baths, and area.
- **Advanced Filtering**: Search properties by location and property type (Villa, Apartment, House, etc.) simultaneously.
- **Detailed Property View**: Full property details with description, multi-image gallery, and overview specs.
- **Seller Inquiry System**: Contact form for potential buyers to send messages directly to sellers.
- **Authentication**: Secure Login and Registration pages for users.
- **Property Management**: Dedicated page for users and admins to add and list new properties.
- **Responsive Design**: Optimized for both desktop and mobile viewing.

### ⚙️ Backend (Spring Boot)
- **RESTful API**: Clean and efficient endpoints for property management, user authentication, and inquiries.
- **Spring Security & JWT**: State-of-the-art security using JSON Web Tokens (JWT) for stateless authentication.
- **File Storage Service**: Custom service for handling image uploads and serving them to the frontend.
- **JPA/Hibernate**: Object-Relational Mapping (ORM) for seamless database interaction.
- **Cross-Origin Support (CORS)**: Configured to securely communicate with the React frontend.

---

## 🛠️ Tech Stack

### Frontend
- **Framework**: React.js
- **Build Tool**: Vite
- **HTTP Client**: Axios (for API communication)
- **Routing**: React Router DOM
- **Styling**: Modern CSS3 (Standard)

### Backend
- **Framework**: Spring Boot 3.2
- **Language**: Java 17/23
- **Security**: Spring Security, JJWT
- **Persistence**: Spring Data JPA
- **Database**: H2 (In-memory)

---

## 📂 Project Structure

### Backend (`/backend`)
- `com.realestate.controller`: REST Controllers (Auth, Property, File, Inquiry).
- `com.realestate.model`: JPA Entities (User, Property, PropertyImage, Inquiry).
- `com.realestate.repository`: Data access interfaces for H2 Database.
- `com.realestate.service`: Business logic (File Storage, User Details).
- `com.realestate.security`: JWT and Security configuration.

### Frontend (`/frontend`)
- `src/components`: Reusable components like `Navbar` and `PropertyCard`.
- `src/pages`: Main views (Home, PropertyDetails, AddProperty, Auth).
- `src/services`: API utility for communicating with the Spring Boot backend.
- `src/context`: Authentication context for managing user state.

---

## 🗄️ Database Information
The system uses the **H2 Database**, an embedded relational database that runs in-memory during development.

- **URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (empty)
- **Console Access**: When the backend is running, you can access the database console at `http://localhost:8080/h2-console`.

### Database Schema
- **Users**: Stores user credentials, emails, and roles (USER/ADMIN).
- **Properties**: Stores property details, price, location, and owner information.
- **Property Images**: Links multiple image URLs to a single property listing.
- **Inquiries**: Stores contact messages from interested buyers.

---

## 🏁 Getting Started

### 1. Start the Backend
Navigate to the `backend` folder and use the portable Maven provided:
```powershell
cd backend
.\maven_portable\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
```
The server will start at `http://localhost:8080`.

### 2. Start the Frontend
Navigate to the `frontend` folder and run:
```powershell
cd frontend
npm install
npm run dev
```
The application will be available at the URL shown in your terminal (usually `http://localhost:5173`).

---

**Developed for Real Estate Portfolio Project.**
