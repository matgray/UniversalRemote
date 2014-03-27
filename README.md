Universal Remote
======================
*Copyright 2014. Mathew Gray. This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.*


This suite containing two servlets and a java swing client that allows for your computer's keyboard to be controlled by anything that can access the internet.  Supports multiple clients.  The java swing client does _not_ host a server, so no need to be on the same network as the computer you are controlling

##Use Cases

#####Amazon Instant Video Remote Control

With the Android App, you can send keystrokes to your computer.  Simply bind "Space" to a button, and "Left Arrow" and "Right Arrow" to another to be able to control the movie from the comfort of your couch

#####PowerPoint Remote Control

No need to buy and keep track of a clicker and a dongle to plug into your computer.  Simply use the Android app to bind a key to "Left" and another key to "Right" and boom you have a PowerPoint remote

#####Security

Be able to lock your computer from anywhere.  Just a tap of a button and you can lock your computer's screen.

##Technical Details

The app submits a GET request with a valid session id (the id is given to each computer client that connects) and a sequence of keystrokes to the HttpServlet.  The servlet parses the sequence of keystrokes and verifies that they are valid (ie every key is released, and no key is released without first being pressed).  If the sequence is valid, the request is forwarded to a different server hosted in the same Tomcat container. This servlet is in charge of maintaining an open socket with the Computer Clients.  When a request is received from the HttpServlet, the session id is looked up and the keystrokes are forwarded to the computer client, which will then perform the keystrokes.

The Android App allows for you to get the session id of a client by scanning in a QR code, and then map buttons how you see fit.  Programmed buttons are persisted, along with their user-customized label
