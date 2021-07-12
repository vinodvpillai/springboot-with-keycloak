# Spring Boot Integration with KeyCloak - Spring Boot Keycloak Service
This project provide guidelines for development team to follow while integration KeyCloak with Spring Boot code/PoCs.
##
### Prerequisites
- JDK 1.8
- Maven
- Mysql
- KeyCloak

## Quick Start

### Clone source
```
git clone https://github.com/vinodvpillai/springboot-with-keycloak.git
cd springboot-with-keycloak
```

##### MySQL (Docker): 
```
docker run -d \
		-p 3307:3306 \
		-e MYSQL_ROOT_PASSWORD=admin \
		-e MYSQL_DATABASE=keycloakdb \
		-e MYSQL_USER=sa \
		-e MYSQL_PASSWORD=admin \
		-e MYSQL_ROOT_PASSWORD=admin \
		--name mysql-standalone \
		--net keycloak-network \
		mysql:5.6
```

##### KeyCloak (Docker): (Need to add appropriate Realm, Client Configuration, Roles and Users.)
```
docker run -d \
		-p 8080:8080  \
		-e KEYCLOAK_USER=admin  \
		-e KEYCLOAK_PASSWORD=admin  \
		--name keycloak \
		--net keycloak-network \
		quay.io/keycloak/keycloak:14.0.0
```

### Build
```
mvn clean package
```

### Run
```
java -jar  /home/agent/springboot-with-keycloak.jar
```

### Endpoint details:

##### 1. Get token from Keycloak (CURL Request):
```
curl -X POST \
  http://localhost:8080/auth/realms/springkeycloak/protocol/openid-connect/token \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'Postman-Token: d7dc2b07-cc24-44c7-b201-d279804063ae' \
  -H 'cache-control: no-cache' \
  -d 'grant_type=password&client_id=springboot-keycloak&username=vinod&password=vinod&client_secret=84a4977c-ffc8-4d40-80f9-33df6b8cb2e4&undefined='
```

##### 2. Welcome Endpoint no authentication required (CURL Request):
```
curl -X GET \
  http://localhost:8082/v1/welcome \
  -H 'Postman-Token: 422084d6-5829-484c-8dee-3cc1413b826a' \
  -H 'cache-control: no-cache'
```

##### 3. Welcome, Admin endpoint only access by users having admin role (CURL Request):
```
curl -X GET \
  http://localhost:8082/v1/admin \
  -H 'Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTM1FLMjVTT3JiTVAxR2dsX0RldWdhZzVUV3A5WjhvemV6d1paM2FpY2lVIn0.eyJleHAiOjE2MjYwODQzNTgsImlhdCI6MTYyNjA4MzQ1OCwianRpIjoiYjYyZjU2MTYtMTRiYi00MzZjLWE0OTQtOWY5OTdhZjA0NGE4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3NwcmluZ2tleWNsb2FrIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjIzZTVjNzliLWM3OTAtNDlhOS1hNDM5LWRiMzg3Y2UzOGRkYSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNwcmluZ2Jvb3Qta2V5Y2xvYWsiLCJzZXNzaW9uX3N0YXRlIjoiZDQwOWQyZGUtMjBhZS00ODU3LWE0ODItMmZhMjBkNmY2MmFhIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYXBwLWFkbWluIiwidW1hX2F1dGhvcml6YXRpb24iLCJhcHAtdXNlciIsImRlZmF1bHQtcm9sZXMtc3ByaW5na2V5Y2xvYWsiXX0sInJlc291cmNlX2FjY2VzcyI6eyJzcHJpbmdib290LWtleWNsb2FrIjp7InJvbGVzIjpbImFkbWluIiwidXNlciJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImVtcGxveWVlMyJ9.VLh0Ya7fIqEhCjfEuJpoq7RXQ8doxB4TBg067PbUxgAnJ-r0tjvqRLxdzmP-4m1WtCY5U-JU8_6kyPoi8QWvTluQE-pnG9nGvKK1-H-13Gve9Nn_puK4IPLZz1O87QeeXaTd5ey7RWI9pHSfIW0OrF5X0ot8BzdVr3mUgVrm8J04HY-CEwAq8GecMdZb8RYFhkaTscD0i5KwVFY8MfTr_88l4CHflGzf20yY9eDzfh6EVSQ1TFLWuBUiDqb2qXCe_TbKvKNj4zDkj0DixYY05TeigK2Q70MLu7nXEZHadqh5NIKh3o0U_lHPvZod1NeHuJc8nv_inp_QtgjpnxkiQA' \
  -H 'Postman-Token: 575bd50d-84e4-42c6-9353-2f44e00f1d5d' \
  -H 'cache-control: no-cache'
```

##### 4. CustomerController - Add Customer, secured endpoint only access by users having user or admin role. (CURL Request):

```
curl -X POST \
  http://localhost:8082/v1/customers \
  -H 'Accept-Language: en' \
  -H 'Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTM1FLMjVTT3JiTVAxR2dsX0RldWdhZzVUV3A5WjhvemV6d1paM2FpY2lVIn0.eyJleHAiOjE2MjYwODQxODMsImlhdCI6MTYyNjA4MzI4MywianRpIjoiZjUxZTkxY2ItNmQzNi00NmU2LWFlZGYtMDU3YTljY2VjM2NlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3NwcmluZ2tleWNsb2FrIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjUzMWU2ZmNhLWNjNzktNGFiZC05YjllLTUwOTFhMjIzM2Q1YiIsInR5cCI6IkJlYXJlciIsImF6cCI6InNwcmluZ2Jvb3Qta2V5Y2xvYWsiLCJzZXNzaW9uX3N0YXRlIjoiZDhhN2EyODQtMWY0Ny00ZjE3LTliNTAtNjk4NmU3OTVjNjU0IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJhcHAtdXNlciIsImRlZmF1bHQtcm9sZXMtc3ByaW5na2V5Y2xvYWsiXX0sInJlc291cmNlX2FjY2VzcyI6eyJzcHJpbmdib290LWtleWNsb2FrIjp7InJvbGVzIjpbInVzZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJlbXBsb3llZTEifQ.GTlfz2Q0LFSmD2Nuo7zK71qZH5Z91DLdPDcCWzHRaJE2XUggoPCo6087U0p1P3OqsQxhePj2rggP1fpPWP2wEzKvdkcdSfCBbcrVwb7lvo-VsBFDTZfE2SYbfHf5I0EhxCcYaDQmN9CvwOCqHub8aL9tmNJKTXLd3He5FByNwrkQjy_Jq0NneLP9Po0n4zfVy9TZyx_TIN-V7HXBQyu2pGRS4OI4PocsLzMHb8MITOujsC7YWTOt8AkgoioF63b2khGXNz9VGU0hHIVBOeM8IAXsHgc8zbrAShxm9SCYVa-bW3veQbvd1oKo0HcWnuEcC7GYu1h-IitlWak_rgjcIg' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 33a5bee5-6ebc-4adf-84a0-a179b263f0de' \
  -H 'cache-control: no-cache' \
  -d '{
	"name": "Vinod",
	"emailId": "vinod@yopmail.com",
	"password": "vinod",
	"address": "Gujarat"
}'
```
##### 5. CustomerController - Get Customer, secured endpoint only access by users having user or admin role. (CURL Request):

```
curl -X GET \
  http://localhost:8082/v1/customers/vinod@yopmail.com \
  -H 'Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTM1FLMjVTT3JiTVAxR2dsX0RldWdhZzVUV3A5WjhvemV6d1paM2FpY2lVIn0.eyJleHAiOjE2MjYwODQzNTgsImlhdCI6MTYyNjA4MzQ1OCwianRpIjoiYjYyZjU2MTYtMTRiYi00MzZjLWE0OTQtOWY5OTdhZjA0NGE4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3NwcmluZ2tleWNsb2FrIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjIzZTVjNzliLWM3OTAtNDlhOS1hNDM5LWRiMzg3Y2UzOGRkYSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNwcmluZ2Jvb3Qta2V5Y2xvYWsiLCJzZXNzaW9uX3N0YXRlIjoiZDQwOWQyZGUtMjBhZS00ODU3LWE0ODItMmZhMjBkNmY2MmFhIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYXBwLWFkbWluIiwidW1hX2F1dGhvcml6YXRpb24iLCJhcHAtdXNlciIsImRlZmF1bHQtcm9sZXMtc3ByaW5na2V5Y2xvYWsiXX0sInJlc291cmNlX2FjY2VzcyI6eyJzcHJpbmdib290LWtleWNsb2FrIjp7InJvbGVzIjpbImFkbWluIiwidXNlciJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImVtcGxveWVlMyJ9.VLh0Ya7fIqEhCjfEuJpoq7RXQ8doxB4TBg067PbUxgAnJ-r0tjvqRLxdzmP-4m1WtCY5U-JU8_6kyPoi8QWvTluQE-pnG9nGvKK1-H-13Gve9Nn_puK4IPLZz1O87QeeXaTd5ey7RWI9pHSfIW0OrF5X0ot8BzdVr3mUgVrm8J04HY-CEwAq8GecMdZb8RYFhkaTscD0i5KwVFY8MfTr_88l4CHflGzf20yY9eDzfh6EVSQ1TFLWuBUiDqb2qXCe_TbKvKNj4zDkj0DixYY05TeigK2Q70MLu7nXEZHadqh5NIKh3o0U_lHPvZod1NeHuJc8nv_inp_QtgjpnxkiQA' \
  -H 'Postman-Token: 9d2d77eb-e7d7-49b2-b4d3-8ecef82bb6be' \
  -H 'cache-control: no-cache'
```

##### 6. CustomerController - Update customer, secured endpoint only access by users having user or admin role. (CURL Request):
```
curl -X PUT \
  http://localhost:8082/v1/customers/vinod@yopmail.com \
  -H 'Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTM1FLMjVTT3JiTVAxR2dsX0RldWdhZzVUV3A5WjhvemV6d1paM2FpY2lVIn0.eyJleHAiOjE2MjYwODQxODMsImlhdCI6MTYyNjA4MzI4MywianRpIjoiZjUxZTkxY2ItNmQzNi00NmU2LWFlZGYtMDU3YTljY2VjM2NlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3NwcmluZ2tleWNsb2FrIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjUzMWU2ZmNhLWNjNzktNGFiZC05YjllLTUwOTFhMjIzM2Q1YiIsInR5cCI6IkJlYXJlciIsImF6cCI6InNwcmluZ2Jvb3Qta2V5Y2xvYWsiLCJzZXNzaW9uX3N0YXRlIjoiZDhhN2EyODQtMWY0Ny00ZjE3LTliNTAtNjk4NmU3OTVjNjU0IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJhcHAtdXNlciIsImRlZmF1bHQtcm9sZXMtc3ByaW5na2V5Y2xvYWsiXX0sInJlc291cmNlX2FjY2VzcyI6eyJzcHJpbmdib290LWtleWNsb2FrIjp7InJvbGVzIjpbInVzZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJlbXBsb3llZTEifQ.GTlfz2Q0LFSmD2Nuo7zK71qZH5Z91DLdPDcCWzHRaJE2XUggoPCo6087U0p1P3OqsQxhePj2rggP1fpPWP2wEzKvdkcdSfCBbcrVwb7lvo-VsBFDTZfE2SYbfHf5I0EhxCcYaDQmN9CvwOCqHub8aL9tmNJKTXLd3He5FByNwrkQjy_Jq0NneLP9Po0n4zfVy9TZyx_TIN-V7HXBQyu2pGRS4OI4PocsLzMHb8MITOujsC7YWTOt8AkgoioF63b2khGXNz9VGU0hHIVBOeM8IAXsHgc8zbrAShxm9SCYVa-bW3veQbvd1oKo0HcWnuEcC7GYu1h-IitlWak_rgjcIg' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: b96dcdf7-3106-4205-8c10-dcde74383bb6' \
  -H 'cache-control: no-cache' \
  -d '{
	"name": "Vinod Pillai",
	"address": "Ahmedabad"
}'
```

##### 7. CustomerController - Delete customer, secured endpoint only access by users having admin role. (CURL Request):
```
curl -X DELETE \
  http://localhost:8082/v1/customers/vinod@yopmail.com \
  -H 'Content-Type: bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTM1FLMjVTT3JiTVAxR2dsX0RldWdhZzVUV3A5WjhvemV6d1paM2FpY2lVIn0.eyJleHAiOjE2MjYwODQzNTgsImlhdCI6MTYyNjA4MzQ1OCwianRpIjoiYjYyZjU2MTYtMTRiYi00MzZjLWE0OTQtOWY5OTdhZjA0NGE4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3NwcmluZ2tleWNsb2FrIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjIzZTVjNzliLWM3OTAtNDlhOS1hNDM5LWRiMzg3Y2UzOGRkYSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNwcmluZ2Jvb3Qta2V5Y2xvYWsiLCJzZXNzaW9uX3N0YXRlIjoiZDQwOWQyZGUtMjBhZS00ODU3LWE0ODItMmZhMjBkNmY2MmFhIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYXBwLWFkbWluIiwidW1hX2F1dGhvcml6YXRpb24iLCJhcHAtdXNlciIsImRlZmF1bHQtcm9sZXMtc3ByaW5na2V5Y2xvYWsiXX0sInJlc291cmNlX2FjY2VzcyI6eyJzcHJpbmdib290LWtleWNsb2FrIjp7InJvbGVzIjpbImFkbWluIiwidXNlciJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImVtcGxveWVlMyJ9.VLh0Ya7fIqEhCjfEuJpoq7RXQ8doxB4TBg067PbUxgAnJ-r0tjvqRLxdzmP-4m1WtCY5U-JU8_6kyPoi8QWvTluQE-pnG9nGvKK1-H-13Gve9Nn_puK4IPLZz1O87QeeXaTd5ey7RWI9pHSfIW0OrF5X0ot8BzdVr3mUgVrm8J04HY-CEwAq8GecMdZb8RYFhkaTscD0i5KwVFY8MfTr_88l4CHflGzf20yY9eDzfh6EVSQ1TFLWuBUiDqb2qXCe_TbKvKNj4zDkj0DixYY05TeigK2Q70MLu7nXEZHadqh5NIKh3o0U_lHPvZod1NeHuJc8nv_inp_QtgjpnxkiQA' \
  -H 'Postman-Token: 356700d0-2879-4935-b107-578c20d20c7e' \
  -H 'cache-control: no-cache'
```

