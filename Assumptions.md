# Assumptions

While developing the application I had to make several assumptions.

First of all, I mentioned about user authorization. It's not implemented fully, so I assume 
that on the client side we have some authorization form or enable social network authorization after which
we send a token to the server and either login a user or create a new one. 

Secondly, I assume that only one competition can be active in a moment. There was nothing sad about how many can we run.

Third, client is not very operational because of some wierd problems with $scope in AngularJS. I have some knoledge of Angular
but I didn't have enough time to understand what is wrong this time. But as for now you can see that 
some REST-API methods are run and WebSocket connection is established with every second update. Some of the REST-API methods 
are "administrative" and not supposed to be ran from client. 

Fourth, All the interaction with data base via MyBatis is covered via unit tests. Also I managed to fully test the REST-API
using Postman REST-client. 
