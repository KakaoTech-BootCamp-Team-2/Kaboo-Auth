# Kaboo-Auth

## 사용 기술 스택

- Java
- Spring Boot
- Spring Security
- JPA
- Jwt
- Oauth2
- MariaDB
- Redis

## Endpoint

- Endpoint prefix: `/api/auth/member`
- Response Body
    - Type1
        - `couse`: `ENUM("AI", "FULLSTACK", "CLOUD")`
        ```json
        {
            "success": true,
            "message": "요청이 성공적으로 처리되었습니다.",
            "data": {
                "koreaName": "안태진",
                "englishName": "gene",
                "classNum": 1,
                "course": "AI"
            }
        }
        ```
    - Type2
        ```json
        {
            "success": true,
            "message": "요청이 성공적으로 처리되었습니다.",
            "data": "message"
        }
        ``` 

1. 특정 카부인 정보 얻기: `GET ?name={korea_name}`
2. 특정 인원 정보 수정: `POST ?name={korea_name}`
    - Request Body
        - `couse`: `ENUM("AI", "FULLSTACK", "CLOUD")`
        ```json
        {
            "koreaName": "한글 이름",
            "englishName": "gene",
            "className": 1,
            "course": "AI"
        }
        ```
3. 모든 카부인 정보 얻기: `GET /all`
4. 특정 기수인 카부인 정보 얻기: `GET /class/{class}`
5. 특정 인원 자기소개 얻기: `GET /introduce?name={korea_name}`
6. 특정 인원 자기소개 수정: `POST /introduce?name={korea_name}`
    - Request Body
        ```
        "message"
        ```