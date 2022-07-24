##Running application

 - go to docker-compose.yaml file and run the services using green arrows right next to row numbers;
 - go to src.main.java.commissionCalculator.CommissionCalculatorApplication and run the app.
 - to receive the data, just send a request to the endpoint: localhost:8080/api?customer_id=all (please remember that depending on your local configuration port may be different)
   - recommended to use PostMan due to Basic Auth implementation, to retrieve the data just use following credentials (username/password):
   - admin/adminpass or bankemployee/bankpass
 - as ?customer_id= you can pass following parameters:
   - ?customer_id=1 - will return object with commission calculation for one customer
   - ?customer_id=1,2,3 - (separated by comma) will return object with commission calculation for customers with IDs as in request
   - ?customer_id=all or (empty) - will return object with commission calculation for all customers

##Background info

In the task description was specified to use MongoDB version. Up to now I was familiar only with relational databases,
like MySQL or PostGreSQL. So I have to apologize upfront for any beginner mistakes done in the DB configuration, it was my first touch with Mongo 
and I had to learn how to run it quite quickly. I had problems with loading received CSV data. I wanted to use a script located in the folder 
init-mongodb.02-init.sh, but unfortunately it was not working. Therefore I decided to create parser classes for CSV files 
located in .commissionCalculator.dbDataLoader package. It is also converting the transaction_amount field, from
"123,45" to 123.45 value (separator is comma so the line reader is splitting the row to more fields than columns in csv file).

Collections in the DB are being created automatically using script .init-mongodb.01-create-db.js

Task requires also Basic Authentication, but I was not able to recognize who will be the user of this application. So I assumed
that end user will be bank employee. Therefore I crated two users using InMemoryUserDetailsManager, both having access to the endpoint.

I also noticed one impreciseness between task description and sample request-response (point 4). 
Task description requires to provide in response amount of the commission and sum of transactions, but sample responses from point 4
are also including fee in the response instead of commision value. So I decided to put both parameters (fee and commission value) in the response.

Application is also logging each commission calculation request to the db as per task description, using CommissionRequest object. 