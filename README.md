# SuperPayment

## Payment methods for e-commerce

### Trade-offs

- AuthZ / AuthN
- A Voucher can have max & min amount & expiration date
- Voucher code in prod should be two (2) for security, personalization and cognitive load reason around the UUID format.
- There are no updates in DB
- For transaction deduplication, we can use optimistic locking.
- Service validations