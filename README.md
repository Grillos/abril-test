# Rest Api for clients Abril

# Pre requirements
- java 8, docker, maven

# Running app
- cd abril-test
- docker-compose up
- mvn clean package
- java -jar target/abril-test-0.0.1-SNAPSHOT.jar

# Access app
- http://localhost:8080/swagger-ui.html

# Create User
- curl -X POST "http://localhost:8080/v1/users" -H "accept: */*" -H "Content-Type: application/json" -d "[ { \"name\": \"teste\", \"password\": \"123456\" }]"

# Validate User
- curl -X POST "http://localhost:8080/v1/users/validate" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"teste\", \"password\": \"123456\"}"

