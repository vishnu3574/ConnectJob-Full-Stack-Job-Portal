# ConnectJob – Full-Stack Job Portal

## Stack
- Frontend: React.js + Vite + Axios
- Backend: Java 17 + Spring Boot + Spring Data JPA + Spring Security + JWT
- Database: H2 (file-backed for easy demo; can be switched to MySQL)

## Run backend
```bash
cd backend
mvn spring-boot:run
```
Runs on http://localhost:8080

## Run frontend
```bash
cd frontend
npm install
npm run dev
```
Runs on http://localhost:5173

## Demo accounts
Register a new account from the UI. Three sample jobs are seeded automatically.

## MySQL
Replace the H2 datasource properties in `backend/src/main/resources/application.properties` with your MySQL URL, username and password.
