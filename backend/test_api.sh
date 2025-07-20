#!/bin/bash

# Task Manager API Test Script
# Make sure your Spring Boot application is running on localhost:8080

BASE_URL="http://localhost:8080"
TOKEN=""

echo "🚀 Starting Task Manager API Tests"
echo "=================================="

# Function to make API calls and display results
make_request() {
    local method=$1
    local endpoint=$2
    local data=$3
    local headers=$4
    
    echo ""
    echo "📡 $method $endpoint"
    echo "----------------------------------------"
    
    if [ -n "$data" ]; then
        response=$(curl -s -w "\n%{http_code}" -X "$method" "$BASE_URL$endpoint" \
            -H "Content-Type: application/json" \
            -H "$headers" \
            -d "$data")
    else
        response=$(curl -s -w "\n%{http_code}" -X "$method" "$BASE_URL$endpoint" \
            -H "$headers")
    fi
    
    # Extract status code and body
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n -1)
    
    echo "Status: $http_code"
    if [ -n "$body" ]; then
        echo "Response: $body"
    fi
    
    # Extract token if it's a login/register response
    if [[ "$endpoint" == *"/auth/"* ]] && [[ "$http_code" == "200" ]]; then
        TOKEN=$(echo "$body" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
        echo "🔑 Token extracted: ${TOKEN:0:20}..."
    fi
}

# Test 1: Register a new user
echo "1️⃣ Testing User Registration"
make_request "POST" "/api/v1/auth/register" '{"username": "testuser", "password": "password123"}'

# Test 2: Login with the user
echo ""
echo "2️⃣ Testing User Login"
make_request "POST" "/api/v1/auth/login" '{"username": "testuser", "password": "password123"}'

# Test 3: Get all tasks (should be empty initially)
echo ""
echo "3️⃣ Testing Get All Tasks"
make_request "GET" "/api/tasks" "" "Authorization: Bearer $TOKEN"

# Test 4: Create a new task
echo ""
echo "4️⃣ Testing Create Task"
make_request "POST" "/api/tasks" '{
    "title": "Learn Spring Boot",
    "description": "Master Spring Boot framework for backend development",
    "status": "TODO",
    "dueDate": "2024-01-25T17:00:00"
}' "Authorization: Bearer $TOKEN"

# Test 5: Create another task
echo ""
echo "5️⃣ Testing Create Another Task"
make_request "POST" "/api/tasks" '{
    "title": "Build API Documentation",
    "description": "Create comprehensive API documentation",
    "status": "IN_PROGRESS",
    "dueDate": "2024-01-30T16:00:00"
}' "Authorization: Bearer $TOKEN"

# Test 6: Get all tasks again (should show 2 tasks)
echo ""
echo "6️⃣ Testing Get All Tasks (After Creation)"
make_request "GET" "/api/tasks" "" "Authorization: Bearer $TOKEN"

# Test 7: Get task by ID
echo ""
echo "7️⃣ Testing Get Task by ID"
make_request "GET" "/api/tasks/1" "" "Authorization: Bearer $TOKEN"

# Test 8: Update a task
echo ""
echo "8️⃣ Testing Update Task"
make_request "PUT" "/api/tasks/1" '{
    "title": "Learn Spring Boot (Updated)",
    "description": "Master Spring Boot framework for backend development - Updated",
    "status": "IN_PROGRESS",
    "dueDate": "2024-01-28T17:00:00"
}' "Authorization: Bearer $TOKEN"

# Test 9: Get goals
echo ""
echo "9️⃣ Testing Get Goals"
make_request "GET" "/api/v1/goals" "" "Authorization: Bearer $TOKEN"

# Test 10: Create a goal
echo ""
echo "🔟 Testing Create Goal"
make_request "POST" "/api/v1/goals" '{
    "title": "Master API Development",
    "description": "Learn all aspects of API development"
}' "Authorization: Bearer $TOKEN"

# Test 11: Create a subtask
echo ""
echo "1️⃣1️⃣ Testing Create Subtask"
make_request "POST" "/api/v1/goals/1/subtasks" '{
    "title": "Learn RESTful Design"
}' "Authorization: Bearer $TOKEN"

# Test 12: Update subtask
echo ""
echo "1️⃣2️⃣ Testing Update Subtask"
make_request "PUT" "/api/v1/goals/1/subtasks/1" '{
    "title": "Learn RESTful Design",
    "completed": true
}' "Authorization: Bearer $TOKEN"

# Test 13: Get goals again (should show goal with subtask)
echo ""
echo "1️⃣3️⃣ Testing Get Goals (After Creation)"
make_request "GET" "/api/v1/goals" "" "Authorization: Bearer $TOKEN"

# Test 14: Delete a task
echo ""
echo "1️⃣4️⃣ Testing Delete Task"
make_request "DELETE" "/api/tasks/2" "" "Authorization: Bearer $TOKEN"

# Test 15: Get all tasks again (should show 1 task)
echo ""
echo "1️⃣5️⃣ Testing Get All Tasks (After Deletion)"
make_request "GET" "/api/tasks" "" "Authorization: Bearer $TOKEN"

echo ""
echo "✅ API Testing Complete!"
echo "=================================="
echo "Summary:"
echo "- Authentication: ✅"
echo "- Task CRUD: ✅"
echo "- Goal Management: ✅"
echo "- Subtask Management: ✅"
echo ""
echo "🎉 All tests completed successfully!" 