# Commission calculator

This is a simple commission calculator

You've got one POST endpoint available that you can use to calculate commission in EUR

```
/calculate-commission
```


## Example input
```json
{
  "date": "2021-01-01",
  "amount": "100.00",
  "currency": "EUR",
  "client_id": 42
}
```

## Example ouput
```json
{
  "amount": "0.05",
  "currency": "EUR"
}
```

## Rules for calculations

- Rule #1: Default pricing - By default the price for every transaction is 0.5% but not less than 0.05€.

- Rule #2: Client with a discount - Transaction price for the client with ID of 42 is 0.05€ (unless other rules set lower commission).

- Rule #3: High turnover discount - Client after reaching transaction turnover of 1000.00€ (per month) gets a discount and transaction commission is 0.03€ for the following transactions.

## Installation

The application uses Spring Boot with MySQL database. You can launch it by opening the project in your IDE, adjusting application.properties db parameters and running CommissionApplication.java or by executing the target/commission-0.0.1-SNAPSHOT.jar file with command line parameters pointing to your database e.g.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/transactions
spring.datasource.username=commissions
spring.datasource.password=
```