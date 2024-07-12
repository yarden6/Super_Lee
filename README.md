
# ADSS_Group_AI
### Storage
Yarden Levy - 208324251
Bar Zuckerman - 207930793

### Employee
itamar Hadida - 206438509
saar riftin - 319071486

## Running the Application
To start the system, run the following command:
java -jar adss2024_v03.jar

### Initial Load

- Upon starting, the system will load all the data previously saved in the database.
- Login Menu: use id and password to login and you will be moved to the private screen.
- In case you have been connected with the store keeper you will be able to continue working on the inventory.
- If it is Sunday, the inventory and defective report will be displayed.
- If any products are near out of stock due to expiration dates, an alert will appear, and an order will be sent to the supplier.
- once the store keeper wants to disconnect he will do exit.

## Main Menu 
### instraction.pdf has more detailed information
After loading, a menu will appear with the following options:

Login:

ID: [type in id]

Password: [type in id]

after that a special screen will appear for each employee.

### for the store keeper only:

Category Operations

Product Operations

Item Operations

Report Operations

The user needs to select the desired operation from the menu to proceed with the system functions.

## External Libraries
The following external libraries are used in this project:

- slf4j-api-1.7.32
- slf4j-simple-1.7.32
- sqlite-jdbc-3.46.0.0
- sqlite-jdbc-3.46.0.0-sources.jar
- JUnit 5.8.1
