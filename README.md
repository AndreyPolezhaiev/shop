Project "Bookshop" was created for comfortable using the service of searching books.
Many apps and online bookshops are difficult to use, and this project solves their problem.
It was made with Spring Boot, Spring Security, Spring Data JPA, Java Core, OOP, Docker, Maven, Git and SQL.
There are controllers, that allow to search books by specific parameters, add books to the shopping cart,
search categories, make an order and also register a user, and log in.
Also there are others for creating a book, changing the status of an order, and adding a category that is allowed for admins only.
People using GitHub can pull my project and make some corrections, improve the project, or use it for their goal.
Making a "Book shop" I faced to make order functionality and authentication.
I correctly built relations between entities and wrote a service part regarding what had to be at the order level.
Regarding authentication, I found another programmers' sollutions and remade a functionality on my way.

There are basic Postman queries that are explained below.
Basic Postman queries for users:
GET:
http://localhost:8080/api/books/search?authors=Braun
- Search the book by params
http://localhost:8080/api/books?sort=price,DESC&sort=title,DESC
- Sort books by params
http://localhost:8080/api/books
- Get all books
http://localhost:8080/api/categories/1/books
- Get books by category ID

POST:
http://localhost:8080/api/orders
- Place an order

Basic postman queries for admins:
POST:
http://localhost:8080/api/books
- Create a book
http://localhost:8080/api/categories
- Create a category

PUT:
http://localhost:8080/api/books/1
- Update the book
http://localhost:8080/api/orders/1
- Update the status of the order

DELETE:
http://localhost:8080/api/books/3
- Delete a book
http://localhost:8080/api/categories/2
- Delete the category
 