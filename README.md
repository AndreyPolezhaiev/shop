## **Description:**

Project "Bookshop" was created for comfortable using the service of searching books.
Many apps and online bookshops are difficult to use, and this project solves their problem.

## **Technologies:**

It was made with Spring Boot, Spring Security, Spring Data JPA, Java Core, OOP, Docker, Maven, Git and SQL.

## **Controllers:**

There are controllers, that allow to search books by specific parameters, add books to the shopping cart,
search categories, make an order and also register a user, and log in.
Also there are others for creating a book, changing the status of an order, and adding a category that is allowed for admins only.

## **For people:**

People using GitHub can pull my project and make some corrections, improve the project, or use it for their goal.

## **Problems:**

Making a "Book shop" I faced to make order functionality and authentication.
I correctly built relations between entities and wrote a service part regarding what had to be at the order level.
Regarding authentication, I found another programmers' sollutions and remade a functionality on my way.

## **Postman queries:**

##### There are basic Postman queries that are explained below.

### **Basic Postman queries for users:**

#### GET:

`http://localhost:8080/api/books/search?authors=Braun`
    - Search the book by params

`http://localhost:8080/api/books?sort=price,DESC&sort=title,DESC`
    - Sort books by params

`http://localhost:8080/api/books` 
    - Get all books

`http://localhost:8080/api/categories/1/books` 
    - Get books by category ID


#### POST:

`http://localhost:8080/api/orders` 
    - Place an order
```json
      {
          "shippingAddress": "bob.street.23higway"
      }
```

`http://localhost:8080/auth/registration` 
    - Register a user 
```json
      {
          "email": "ben@gmail.com",
          "password": "123456", 
          "repeatPassword": "123456", 
          "firstName": "Ben", 
          "lastName": "Osten", 
          "shippingAddress": "ben.address 001" 
      }
```

`http://localhost:8080/auth/login` 
    - Login a user 
```json
      {
          "email": "alice@gmail.com",
          "password": "1234",
          "repeatPassword": "1234"
      }
```
### Basic postman queries for admins:

#### POST:

`http://localhost:8080/api/books` 
    - Create a book 
```json
      {
          "title": "New",
          "author": "New author",
          "isbn": "9781122334455",
          "price": 23,
          "description": "Yet another sample book description.",
          "coverImage": "http://example.com/cover3.jpg"
      }
```

`http://localhost:8080/api/categories` 
    - Create a category 
```json
      {
          "name": "Fantasy",
          "description": "fantasy books"
      }
```
#### PUT:

`http://localhost:8080/api/books/1` 
    - Update the book 
```json
      {   
          "title": "Book Udated 444",  
          "author": "Author Updated 333",  
          "isbn": "978112adfe33",  
          "price": 29.99,  
          "description": "Updated Yet another sample book description.",  
          "coverImage": "http://example.com/cover3.jpg 111"  
      }
```

`http://localhost:8080/api/orders/1`
    - Update the status of the order
```json
      {
          "status": "Pending"
      }
```

#### DELETE:

`http://localhost:8080/api/books/3` 
    - Delete the book \
`http://localhost:8080/api/categories/2` 
    - Delete the category
 
## **Loom video with instruction to my project**
https://www.loom.com/share/fb5759396c3041d5aa479c24c61cdcb7?sid=aa759aa3-531c-4e6c-8676-cba33fc29011