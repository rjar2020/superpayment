# SuperPayment

## Payment methods for e-commerce

### Trade-offs, To-Do and Fun Facts

* Pending 
  1. Widget AuthZ / AuthN.
* Refactor, endless ->
  1. Refactor PaymentService tests, improve interaction with the Repository. 
  2. Spock for testing.
  3. Use a lightweight JDBC lib, like jdbi instead of JPA. 
  4. Services' validations.
  5. No Updates at DB level, just inserts and deletes.
  6. Soft deletion, with a maintenance job.
  7. In Payment service, limiting the max page size.
  8. In payment.html, extract the CSS and in genara creating an architecture for styles and scripts.
* Infrastructure ->
  1. Observability (metrics, logs, tracing)
  2. Explore rate liming for SpringBoot.
  3. AuthZ / AuthN (SpringBoot Security) -> csrf-token handling and go beyond basic auth.
  4. Swagger for documenting the API.
  5. Certificate for HTTPS, it's a must, for enhancing security.
  6. Heroku.
* Backoffice pagination ->
  1. Implementing and InHouse pagination model.
  2. Allowing customers to set the page size.
  3. Table's fixed size.
* Notes ->
  1. A Voucher can have max & min amount & expiration date.
  2. Voucher code and id in prod should be two (2) different things for security, personalization and cognitive load reason around the UUID format.
  3. Version in DB, for concurrency-> For transaction deduplication, we can use optimistic locking.
