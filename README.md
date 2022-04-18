# Commerce Services - Technical Interview

## Developer part
### How to run Java Project

Building
```shell
./mvnw compile
```

Test
```shell
./mvnw test
```

Start the application

```shell
./mvnw spring-boot:run
```

### How to test

1. CREATE A NEW ORDER.

### REQUEST (POST)

http://localhost:8080/books_stock/makeOrder

BODY:

```
 {

  "userOrder" :  [

            {
                "id": "14e05a52-eec7-4ff4-af1b-d6fd1cb90207", -- id of the book i want to buy
                "name": "irure aute aliquip occaecat anim", -- name of book
                "quantity": 2 -- quantity of books i want to buy
            }

    ]

}
```

### RESPONSE 

Id of the order: 1.810001012623587E9


2. GET ALL THE ORDERS.

### REQUEST (GET)

http://localhost:8080/books_stock/orders

### RESPONSE 
```
[
    {
        "id": "1.829429931559126E9", -- id of the request 
        "status": "SUCCESFUL", -- status of the request
        "orderDate": "2022-04-13T22:28:55.471125", 
        "bookStocks": [ -- book that i have bought
            {
                "id": "14e05a52-eec7-4ff4-af1b-d6fd1cb90207",
                "name": "irure aute aliquip occaecat anim",
                "quantity": 1 -- current stock of the book 
            }
        ]
    },
    {
        "id": "1.810001012623587E9",
        "status": "CANCELLED",
        "orderDate": "2022-04-13T22:29:02.660066",
        "bookStocks": [
            {
                "id": "14e05a52-eec7-4ff4-af1b-d6fd1cb90207",
                "name": "irure aute aliquip occaecat anim",
                "quantity": 1
            }
        ]
    }
]

```
## Devops part

### How to run docker

Add this 2 lines in /etc/hosts file:
```
127.0.0.1 api.oursaas.org
127.0.0.1 static.oursaas.org
```
### How to run docker

```shell
./docker compose up -d
```

### How to test aws services

#### dynamodb
```
api.oursaas.org:4565
```

#### postgres
```
api.oursaas.org:4231
```
#### s3
```
static.oursaas.org:4566
```
### How to test Jenkinsfile

1. Install locally Jenkins: https://www.jenkins.io/doc/pipeline/tour/getting-started/
2. Create pipeline:
     2.1 New Item
     2.2 Pipeline
     2.3 Pipeline -> Definition -> Pipeline script from SCM
     2.4 SCM -> Git
     2.5 Repository URL -> https://github.com/frannciscolopez/bookStore
     2.6 Apply
3. Go to the pipeline.
4. Build now.

