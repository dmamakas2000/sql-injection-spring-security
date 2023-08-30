# SQL Injection Attack Scenario

1. First type in the *username* and *password* fields the SQL code: ```admin'or'1'='1``` Then, press the login button.

This particular form should have theoretically generated the following query:

````sql
SELECT * FROM users WHERE username=’admin’
or’1’=’1’ AND password=’admin’or’1’=’1’
````

Which is theoretically true, and should link the user to the application. However, the result we get is completely different (see the below image).

2. The application identifies the attempt, and rejects the connection.
