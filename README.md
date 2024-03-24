# SuperPayment

## Payment methods for e-commerce

### Trade-offs

- AuthZ / AuthN (Springboot Security)
- A Voucher can have max & min amount & expiration date
- Voucher code and id in prod should be two (2) different things for security, personalization and cognitive load reason around the UUID format.
- Version in DB, for concurrency-> For transaction deduplication, we can use optimistic locking.
- Service validations
- Certificate for HTTPS
- Interfaces
- Spock
- Refactor
- Backoffice pagination
