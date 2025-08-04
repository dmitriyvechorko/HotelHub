# HotelHub API
RESTful API для управления отелями. Позволяет создавать, просматривать, искать отели, добавлять удобства, а также получать статистику.
## Технологии
- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database (встроенная, для разработки)
- Liquibase (миграции базы данных)
- Lombok
- Swagger (OpenAPI 3.0) для документации API
## Требования
- Java 17 или выше
- Maven 3.8.1 или выше
## Запуск приложения
1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/ваш-проект/hotelhub.git
   cd hotelhub
   ```
2. Соберите проект с помощью Maven:
   ```bash
   mvn clean install
   ```
3. Запустите приложение:
   ```bash
   mvn spring-boot:run
   ```
Приложение будет доступно по адресу: `http://localhost:8092`
## Документация API
После запуска приложения документация доступна через Swagger UI:
- **Swagger UI**: `http://localhost:8092/property-view/swagger-ui.html`
- **OpenAPI спецификация**: `http://localhost:8092/property-view/api-docs`
## Примеры запросов
### Создание отеля
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
### Получение списка отелей
```bash
curl -X GET "http://localhost:8092/property-view/hotels"
```
### Поиск отелей по городу
```bash
curl -X GET "http://localhost:8092/property-view/search?city=Minsk"
```
### Добавление удобств к отелю
```bash
curl -X POST "http://localhost:8092/property-view/hotels/1/amenities" \
  -H "Content-Type: application/json" \
  -d '["Free WiFi", "Swimming Pool", "Parking"]'
```
### Получение гистограммы по городам
```bash
curl -X GET "http://localhost:8092/property-view/histogram/city"
```
