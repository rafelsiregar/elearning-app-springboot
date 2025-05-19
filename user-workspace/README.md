# E-Learning Platform

A full-stack e-learning platform with quiz management, assignments, and grading features.

## Project Structure

- `backend/`: Spring Boot backend application
- `src/`: Next.js frontend application

## Backend Setup (Spring Boot)

1. Navigate to the backend directory:
```bash
cd backend
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`

## Frontend Setup (Next.js)

1. Install dependencies:
```bash
npm install
```

2. Run the development server:
```bash
npm run dev
```

The frontend will start on `http://localhost:3000`

## Features

- Quiz management with multiple question types:
  - Multiple choice
  - True/False
  - Short answer
  - Essay
  - Matching
- Assignment management
- Grade tracking
- Modern UI with Tailwind CSS

## API Endpoints

### Quizzes
- GET `/api/quizzes`: Get all quizzes
- GET `/api/quizzes/{id}`: Get quiz by ID
- POST `/api/quizzes`: Create new quiz
- PUT `/api/quizzes/{id}`: Update quiz
- DELETE `/api/quizzes/{id}`: Delete quiz

### Assignments
- GET `/api/assignments`: Get all assignments
- GET `/api/assignments/{id}`: Get assignment by ID
- POST `/api/assignments`: Create new assignment
- PUT `/api/assignments/{id}`: Update assignment
- DELETE `/api/assignments/{id}`: Delete assignment

### Grades
- GET `/api/grades`: Get all grades
- GET `/api/grades/{id}`: Get grade by ID
- POST `/api/grades`: Create new grade
- PUT `/api/grades/{id}`: Update grade
- DELETE `/api/grades/{id}`: Delete grade
