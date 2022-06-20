# spring-boot-demo-assignment

Demo spring boot application with REST APIs to validate Finnish Social Security Number (SSN) and Exchange amount with target currencies. Also includes Quartz Scheduled job to fetch exchange rates. 

## Installation

Java 11 or higher is required to be installed on locally.

Example with Homebrew on MacOS:
```
$ brew update
$ brew install java11
```

## Start Application
```
./gradlew bootRun
```

## Run Tests
```
./gradlew clean test
```

## Test APIs

CURL request examples for using APIs. These requests could be imported to the Postman.

Validate SSN:
```
curl --location --request POST 'localhost:8080/validate_ssn?ssn=180594-899W&country_code=FI'
```

Exchange Amount:
```
curl --location --request GET 'localhost:8080/exchange_amount?from=EUR&to=SEK&from_amount=10'
```

## Enable Quartz Scheduler job

Quartz job is not enabled by default.

<b>Note</b>. By enabling Quartz scheduler to fetch exchange rates, a few properties must be set on.

API_KEY for the used external exchange rate API could be get from here: https://apilayer.com/marketplace/exchangerates_data-api

```
exchange.rate.apikey=YOUR_API_KEY 
quartz.enabled=true
```
