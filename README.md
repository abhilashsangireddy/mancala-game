# Documentation

Since this is a two player [symmetric game](https://en.wikipedia.org/wiki/Symmetric_game), the code is designed in such a way that it is disassociated with the player it is dealing with. Irrespective of player one/ two the services are written in such a way that they follow the same logic for computation.

Java documentation has been written and generated for this code to explain each of its components in depth.
[Link to java documentation](https://abhilashsangireddy.github.io/mancala-documentation/java/index.html)

The endpoints exposed by this game are also documented using swagger and it can be found in the following link:
[Link to API documentation](https://abhilashsangireddy.github.io/mancala-documentation/swagger/index.html)


# Setup:



This project contains the source code of Mancala game a two player board game. This application uses **SpringBoot** for backend and **React** for frontend.

The following steps describe the process to run this application:
1. Clone/Download the zip into a directory. The directory in which the code is present to will be referred to as *GameDirectory* going forward.

2. Run the following commands to start the frontend server:
```
cd GameDirectory/frontend
nmp install
npm start
```
Running this will download all the dependencies related to the user interface and will start the server on localhost:3000

Once you open localhost:3000, you should be seeing a screen like this:

![Mancala game landing page](https://abhilashsangireddy.github.io/mancala-documentation/pics/1.png)

3. Run the following commands to start the backend server:
```
cd GameDirectory
./gradlew run
```
Running this will download all the dependencies related to the backend and will start the server on localhost:8080


# Playing the game

Once the setup is done, enter the names of two players and send the request to start the game.
On clicking **start** button, you will be redirected to a page like this:

![enter image description here](https://abhilashsangireddy.github.io/mancala-documentation/pics/2.png)

The ⛹️ logo denotes the player who is supposed to take the next turn. In the above mentioned example, the next turn is to be played by *playerOne*

Once the end condition is reached, the game will automatically end. If you wish to stop the game midway, just click on the **End Game** button and it will stop the game and declare the player with most number of stones in all of his pits (small and big combined) as the winner. Or as a tie game if both of them have equal scores.

![enter image description here](https://abhilashsangireddy.github.io/mancala-documentation/pics/3.png)
