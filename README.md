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

## Installation

The application uses Spring Boot with MySQL database. You can launch it by opening the project and running CommissionApplication.java or by executing the target/commission-0.0.1-SNAPSHOT.jar file with parameters pointing to your database e.g.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/transactions
spring.datasource.username=commissions
spring.datasource.password=
```