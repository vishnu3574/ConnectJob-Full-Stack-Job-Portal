# ConnectJob – Full-Stack Job Portal

A full-stack job portal built with **React.js** and **Spring Boot**, designed to connect job seekers with employers through job listings and applications.

## Tech Stack

* React.js
* Java
* Spring Boot
* Spring Data JPA
* MySQL / H2
* REST APIs
* JWT Authentication
* HTML, CSS, JavaScript

## Features

* User registration and login
* Job seeker and employer roles
* Browse and search job listings
* View job details
* Apply for jobs
* Employer job posting
* REST API integration
* JWT-based authentication
* Responsive React interface

## Project Structure

```text
ConnectJob/
├── frontend/     # React.js application
└── backend/      # Spring Boot REST API
```

## How to Run

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Backend

Open the `backend` project in IntelliJ IDEA or Eclipse and run the Spring Boot application.

The frontend communicates with the backend through REST APIs.

## Project Flow

```text
React.js Frontend
       ↓
     Axios
       ↓
Spring Boot REST API
       ↓
Spring Data JPA
       ↓
    Database
```

## Author

**Vishnu Vardhan Akula**
