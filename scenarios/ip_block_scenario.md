# IP Address Blocking Scenario

1. Initially, the user *p3180102* is successfully logged in with the password *password*.
![Scenario 1 - Image 1](images/sc1_im1.png)

2. We log out of the home page, and attempt a new login with the user *p3180000* and a password that is not important to mention as the login is rejected due to an incorrect username.
![Scenario 1 - Image 2](images/sc1_im2.png)

3. We attempt to connect again with the user *admin1* and a password that is also not important to mention as the connection is rejected again.
![Scenario 1 - Image 3](images/sc1_im3.png)

At this point, the system **blocks the IP address for one day**, as there were 2 consecutive failed login attempts to the system dashboard.

4. Trying to log in with any combination of username, password now redirects us to the following window.
![Scenario 1 - Image 4](images/sc1_im4.png)
