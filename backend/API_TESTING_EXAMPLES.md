# Task Manager API Testing Examples

This document provides comprehensive examples for testing all endpoints in the Task Manager API.

## Base URL
```
http://localhost:8080
```

## Authentication Endpoints

### 1. Register User
**POST** `/api/v1/auth/register`

**Request Body:**
```json
{
    "username": "john_doe",
    "password": "password123"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "john_doe"
}
```

### 2. Login User
**POST** `/api/v1/auth/login`

**Request Body:**
```json
{
    "username": "john_doe",
    "password": "password123"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "john_doe"
}
```

## Task Management Endpoints

### 3. Get All Tasks
**GET** `/api/tasks`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

**Response:**
```json
[
    {
        "id": 1,
        "title": "Complete Project Documentation",
        "description": "Write comprehensive documentation for the project",
        "status": "IN_PROGRESS",
        "dueDate": "2024-01-15T10:00:00",
        "createdAt": "2024-01-01T09:00:00",
        "updatedAt": "2024-01-10T14:30:00"
    },
    {
        "id": 2,
        "title": "Review Code Changes",
        "description": "Review pull requests and provide feedback",
        "status": "TODO",
        "dueDate": "2024-01-20T16:00:00",
        "createdAt": "2024-01-02T11:00:00",
        "updatedAt": "2024-01-02T11:00:00"
    }
]
```

### 4. Get Task by ID
**GET** `/api/tasks/{id}`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

**Response:**
```json
{
    "id": 1,
    "title": "Complete Project Documentation",
    "description": "Write comprehensive documentation for the project",
    "status": "IN_PROGRESS",
    "dueDate": "2024-01-15T10:00:00",
    "createdAt": "2024-01-01T09:00:00",
    "updatedAt": "2024-01-10T14:30:00"
}
```

### 5. Create New Task
**POST** `/api/tasks`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

**Request Body:**
```json
{
    "title": "Implement User Authentication",
    "description": "Add JWT-based authentication to the application",
    "status": "TODO",
    "dueDate": "2024-01-25T17:00:00"
}
```

**Response:**
```json
{
    "id": 3,
    "title": "Implement User Authentication",
    "description": "Add JWT-based authentication to the application",
    "status": "TODO",
    "dueDate": "2024-01-25T17:00:00",
    "createdAt": "2024-01-12T10:15:00",
    "updatedAt": "2024-01-12T10:15:00"
}
```

### 6. Update Task
**PUT** `/api/tasks/{id}`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

**Request Body:**
```json
{
    "title": "Implement User Authentication",
    "description": "Add JWT-based authentication with refresh tokens",
    "status": "IN_PROGRESS",
    "dueDate": "2024-01-30T17:00:00"
}
```

**Response:**
```json
{
    "id": 3,
    "title": "Implement User Authentication",
    "description": "Add JWT-based authentication with refresh tokens",
    "status": "IN_PROGRESS",
    "dueDate": "2024-01-30T17:00:00",
    "createdAt": "2024-01-12T10:15:00",
    "updatedAt": "2024-01-12T15:30:00"
}
```

### 7. Delete Task
**DELETE** `/api/tasks/{id}`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

**Response:** `204 No Content`

## Goal Management Endpoints

### 8. Get User Goals
**GET** `/api/v1/goals`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

**Response:**
```json
[
    {
        "id": 1,
        "title": "Learn Spring Boot",
        "description": "Master Spring Boot framework for backend development",
        "subtasks": [
            {
                "id": 1,
                "title": "Complete Spring Boot Basics",
                "completed": true
            },
            {
                "id": 2,
                "title": "Learn Spring Security",
                "completed": false
            }
        ]
    }
]
```

### 9. Create Goal
**POST** `/api/v1/goals`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

**Request Body:**
```json
{
    "title": "Build a Task Manager App",
    "description": "Create a full-stack task management application"
}
```

**Response:**
```json
{
    "id": 2,
    "title": "Build a Task Manager App",
    "description": "Create a full-stack task management application",
    "subtasks": []
}
```

### 10. Create Subtask
**POST** `/api/v1/goals/{goalId}/subtasks`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

**Request Body:**
```json
{
    "title": "Design Database Schema"
}
```

**Response:**
```json
{
    "id": 3,
    "title": "Design Database Schema",
    "completed": false
}
```

### 11. Update Subtask
**PUT** `/api/v1/goals/{goalId}/subtasks/{subtaskId}`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

**Request Body:**
```json
{
    "title": "Design Database Schema",
    "completed": true
}
```

**Response:**
```json
{
    "id": 3,
    "title": "Design Database Schema",
    "completed": true
}
```

## Admin Endpoints (Requires ADMIN Role)

### 12. Get Inactive Users
**GET** `/api/v1/admin/inactive-users`

**Headers:**
```
Authorization: Bearer <admin_jwt_token>
```

**Response:**
```json
[
    {
        "id": 2,
        "username": "inactive_user",
        "status": "INACTIVE",
        "createdAt": "2024-01-01T10:00:00"
    }
]
```

### 13. Activate User
**PUT** `/api/v1/admin/users/{userId}/activate`

**Headers:**
```
Authorization: Bearer <admin_jwt_token>
```

**Response:**
```json
{
    "id": 2,
    "username": "inactive_user",
    "status": "ACTIVE",
    "createdAt": "2024-01-01T10:00:00"
}
```

### 14. Delete User
**DELETE** `/api/v1/admin/users/{userId}`

**Headers:**
```
Authorization: Bearer <admin_jwt_token>
```

**Response:** `204 No Content`

## Testing with cURL Examples

### Authentication
```bash
# Register
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "password123"}'

# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "password123"}'
```

### Tasks
```bash
# Get all tasks
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer <your_jwt_token>"

# Create task
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Task",
    "description": "This is a test task",
    "status": "TODO",
    "dueDate": "2024-01-25T17:00:00"
  }'

# Update task
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Test Task",
    "description": "This is an updated test task",
    "status": "IN_PROGRESS",
    "dueDate": "2024-01-30T17:00:00"
  }'

# Delete task
curl -X DELETE http://localhost:8080/api/tasks/1 \
  -H "Authorization: Bearer <your_jwt_token>"
```

### Goals
```bash
# Get goals
curl -X GET http://localhost:8080/api/v1/goals \
  -H "Authorization: Bearer <your_jwt_token>"

# Create goal
curl -X POST http://localhost:8080/api/v1/goals \
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn API Testing",
    "description": "Master API testing techniques"
  }'

# Create subtask
curl -X POST http://localhost:8080/api/v1/goals/1/subtasks \
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn cURL"
  }'

# Update subtask
curl -X PUT http://localhost:8080/api/v1/goals/1/subtasks/1 \
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn cURL",
    "completed": true
  }'
```

## Testing with Postman

1. **Create a new collection** for Task Manager API
2. **Set up environment variables:**
   - `base_url`: `http://localhost:8080`
   - `auth_token`: (will be set after login)

3. **Authentication Flow:**
   - Register → Extract token from response
   - Login → Extract token from response
   - Set the token in environment variable

4. **Request Headers for authenticated endpoints:**
   ```
   Authorization: Bearer {{auth_token}}
   Content-Type: application/json
   ```

## Error Responses

### 401 Unauthorized
```json
{
    "timestamp": "2024-01-12T10:15:00",
    "status": 401,
    "error": "Unauthorized",
    "message": "Invalid or missing authentication token"
}
```

### 403 Forbidden
```json
{
    "timestamp": "2024-01-12T10:15:00",
    "status": 403,
    "error": "Forbidden",
    "message": "Access denied. Admin role required."
}
```

### 404 Not Found
```json
{
    "timestamp": "2024-01-12T10:15:00",
    "status": 404,
    "error": "Not Found",
    "message": "Task not found with id: 999"
}
```

### 400 Bad Request
```json
{
    "timestamp": "2024-01-12T10:15:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Title is required"
}
```

## Task Status Values

- `TODO`: Task is pending
- `IN_PROGRESS`: Task is currently being worked on
- `DONE`: Task is completed

## Notes

1. **JWT Token**: After successful login/register, extract the token from the response and include it in the `Authorization` header for subsequent requests.

2. **CORS**: The API supports CORS for `http://localhost:4200` (Angular frontend).

3. **Validation**: Request bodies are validated using Bean Validation annotations.

4. **Security**: Admin endpoints require the `ADMIN` role.

5. **Date Format**: Use ISO 8601 format for dates: `YYYY-MM-DDTHH:mm:ss` 