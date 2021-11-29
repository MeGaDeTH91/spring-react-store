<img src="https://res.cloudinary.com/devpor11z/image/upload/v1636835540/spring-react-logo_sj3k7i.png" alt="store-logo" />
<h3><i>Title: </i><br><b>Spring^React Store</b> - great choice for your sales.</h3>

### <i>Introduction: </i><br>
Spring^React store is simple solution for publishing, managing and selling your products with user-friendly UI.
The main goal is to make each seller's products available globally. The online store can be accessed as guest,
registered user with standard privileges and there is also administrative area.

### <i>Technologies: </i><br>
The application consists two parts - MySQL database provider, back-end REST API, based on Spring REST and front-end application,
based on the React library.

### <i>Launch settings: </i><br>
The REST API is located in the 'rest-api' folder, before building and starting the project, please provide the needed configuration in application.properties, needed for MySQL database access and JWT, please check the provided file in the project for reference.<br>
Then configure the Spring project:<br>
You can do this using IntelliJ IDEA or similar software, just add run configuration and hit 'Run' button<br>or<br>
build project with 'mvn clean install' and execute binary. This will start the REST API on
[http://127.0.0.1:8000/](http://127.0.0.1:8000/)
Next, in the 'front-end' folder, in separate command-line window run 'npm install' and 'npm start' command and
this will start the React app on  [http://localhost:3000/](http://localhost:3000/)
After that, it is up to you :)

### <i>Testing: </i><br>
Cypress is used for more complex End-to-End testing with > 85% functionality coverage. Can be started with<br>
'npm run test:e2e' from the 'front-ent' directory.<br>

### <i>Hosting: </i><br>
The application is hosted on [Heroku](https://www.heroku.com) and can be found on the following link:<br>
[https://spring-react-client.herokuapp.com/](https://spring-react-client.herokuapp.com/) <br>
The REST API is hosted also on [Heroku](https://www.heroku.com) and can be found on the following link:<br>
[https://spring-react-rest.herokuapp.com/](https://spring-react-rest.herokuapp.com/)


### <i>Table of contents: </i><br>
1. Public part:<br> 
    * Guest users can access home page with all products listed with reviews section, page with listed categories so user can browse products by category, register, login pages and search by title option.<br>
2. Private part:<br>
    * Standard user – can access all public functionalities plus adding products to shopping cart, viewing and removing products from shopping cart, finalizing order and viewing  recent orders history, exploring and update of profile details. User is able to share product feedback by writing reviews.
    * Administrator – can access all functionalities, which standard user is able to, plus adding, editing and removing products, also adding and editing categories. Administrators can manage users and ban(deactivate) or activate user accounts.
	* Root – can access all functionalities, which administrator is able to, plus giving and removing administrative privileges.
