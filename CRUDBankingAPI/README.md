CRUD BANKING API

Users
- register and login
- CRUD Accounts
  - can only delete if balance is 0
- has many Accounts
- deposit, withdraw, transfer btw Accounts
- view transaction history of Accounts
- boolean admin value 

if user is an admin:
- change other users' admins access
- delete other users
- delete other user accounts

Accounts
- belongs to a user (foreign key)
- has a monetary value (balance)
- has a history (transactions)
- I am adding a title (not null) and description (can be null)

Transactions?
- for account transaction history
- have a transaction type (withdraw, deposit, transfer)
- have an amount
- date of transaction



