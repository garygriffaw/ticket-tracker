# Ticket Tracker
Ticket Tracker is a system for submitting work tickets and keeping track of them through resolution.
The project utilizes Java, Spring, Thymeleaf, html, and css.

At the moment this project uses an H2 database.
The project should be ran from a Java IDE.
After starting the application, open a web browser and go to http://localhost:8080.

A user id is needed to log in.  To create user ids, click on the Register button and fill in the form.
The email address on the Registration form is not currently used to send emails.

There are two types of users in Ticket tracker, User and Admin.
New users are assigned the User role.
An Admin user can update the role a user has.
If there are no Admins yet, open http://localhost:8080/h2-console in a web browser and use the following values.
- JDBC URL: jdbc:h2:mem:mydb
- User Name: sa
- The password should be blank
- Execute "update USER_ACCOUNT set role = 'ROLE_ADMIN' where user_name = '*the admin user id*';"

### Admin Users
USER_ACCOUNT.ROLE: 'ROLE_ADMIN'

Admin users have access to the standard user pages plus the "Users" and "Queues" pages.  This role is intended for people who maintain the Ticket Tracker system.
#### Users
The User pages are used to view and maintain Ticket Tracker users.
The update page can be used to update the user's name, email, role, whether the user id is enabled, and what queues the user has access to.
#### Queues
Queues are used to group tickets.
They could be work teams or departments.
These pages are used to view the list of queues and add new queues to the list.
Some sample Queue values are automatically loaded in the data.sql file.

### Standard Users
USER_ACCOUNT.ROLE: 'ROLE_USER'

All Ticket Tracker users have access to these pages.

#### Home
The Home page shows open tickets that the logged in user created, owns, is assigned to, or are unassigned tickets in the queue(s) the user is assigned to.
Click the "Update" button to view the ticket and make updates.

New tickets are created from the Home page by clicking the "Create New Ticket" button.

#### Update Ticket
The Update Ticket page is used to update certain fields or to add Comments to the ticket.
When certain updates are made to the ticket, a comment is automatically inserted showing the old and new values as well as who made the update.

The following validations are performed when the "Update Ticket" button is clicked.
- When Assigned To is "Unassigned", Status must be "Unassigned".
- When Assigned To is a user, Status must not be "Unassigned".
- When Assigned To is a user, the Queue must be a queue that is assigned to the user.
If the ticket should be assigned to a Queue without knowing a specific user to assign it to, 
change the Assigned To and Status to "Unassigned".

When the Status is changed to Complete, a Closure Comment field appears and is required.

When the Status is changed to Complete or Cancelled, the Closed Date and User fields are automatically populated.

#### My Closed Tickets
The My Closed Tickets page shows a list of all closed tickets created by, owned by, assigned to, or closed by the logged in user.
Click on the "View" button to see the details of the ticket.

#### My Profile
The My Profile page is used by the logged in user to change their name, email, or password.