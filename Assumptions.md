# Assumptions

While developing the application I had to make several assumptions.

First of all, I mentioned about user authorization. It's not implemented fully, so I assume 
that on the client side we have some authorization form or enable social network authorization after which
we send a token to the server and either login a user or create a new one. 

Secondly, I assume that only one competition can be active in a moment. There was nothing sad about how many can we run.

Some of the REST-API methods are "administrative" and not supposed to be ran from client. Before usage add competition and candidates through REST-API

Fourth, All the interaction with data base via MyBatis is covered via unit tests. Also I managed to fully test the REST-API
using Postman REST-client. 
