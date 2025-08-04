# HotelHub API
RESTful API for hotel management. Allows creating, viewing, searching hotels, adding amenities, and getting statistical reports.
## Technologies
- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database (embedded, for development)
- Liquibase (database migrations)
- Lombok
- Swagger (OpenAPI 3.0) for API documentation
- Maven (build tool)
## Requirements
- Java 17 or higher
- Maven 3.8.1 or higher
## Running the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/ваш-проект/hotelhub.git
   cd hotelhub
   ```
2. Build the project with Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
The application will be available at: `http://localhost:8092`
## API Documentation
After starting the application, documentation is available through Swagger UI:
```bash
http://localhost:8092/swagger-ui/index.html
   ```
## Example Requests
### Create a Hotel
```bash
curl -X POST "http://localhost:8092/property-view/hotels" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "DoubleTree by Hilton Minsk",
    "description": "Luxurious rooms with city views",
    "brand": "Hilton",
    "address": {
      "houseNumber": 9,
      "street": "Pobediteley Avenue",
      "city": "Minsk",
      "country": "Belarus",
      "postCode": "220004"
    },
    "contacts": {
      "phone": "+375 17 309-80-00",
      "email": "info@hilton-minsk.com"
    },
    "arrivalTime": {
      "checkIn": "14:00",
      "checkOut": "12:00"
    }
  }'
```
### Get List of Hotels
```bash
curl -X GET "http://localhost:8092/property-view/hotels"
```
### Search Hotels by City
```bash
curl -X GET "http://localhost:8092/property-view/search?city=Minsk"
```
### Add Amenities to a Hotel
```bash
curl -X POST "http://localhost:8092/property-view/hotels/1/amenities" \
  -H "Content-Type: application/json" \
  -d '["Free WiFi", "Swimming Pool", "Parking"]'
```
### Получение гистограммы по городам
```bash
curl -X GET "http://localhost:8092/property-view/histogram/city"
```
