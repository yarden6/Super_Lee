
# ADSS_Group_AI - Storage

Yarden Levy - 208324251
Bar Zuckerman - 207930793

## Running the Application
To start the system, run the following command:
java -jar adss2024_v02.jar

### Initial Load
- Upon starting, the system will load all the data previously saved in the database.
- If it is Sunday, the inventory and defective report will be displayed.
- If any products are near out of stock due to expiration dates, an alert will appear, and an order will be sent to the supplier.

## Main Menu
After loading, a menu will appear with the following options:

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
