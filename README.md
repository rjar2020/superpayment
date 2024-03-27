# SuperPayment

## Payment methods for e-commerce

### Trade-offs, To-Do and Fun Facts

* Refactor, endless ->
  1. Refactor PaymentService tests, improve interaction with the Repository 
  2. Spock for testing
  3. Use a lightweight JDBC lib, like jdbi instead of JPA. 
  4. Services' validations
  5. No Updates at DB level, just inserts and deletes.
  6. Soft deletion, with a maintenance job.
* Infrastructure ->
  1. AuthZ / AuthN (Springboot Security) -> csrf-token handling and go beyond basic auth
  2. Certificate for HTTPS
  3. Heroku
* Backoffice pagination ->
  1. Implementing and InHouse pagination model.
  2. Allowing customers to set the page size.
  3. Fixed the table size.
* Notes ->
  1. A Voucher can have max & min amount & expiration date
  2. Voucher code and id in prod should be two (2) different things for security, personalization and cognitive load reason around the UUID format.
  3. Version in DB, for concurrency-> For transaction deduplication, we can use optimistic locking.
