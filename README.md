
# Noughts and Crosses Game Restful service

## How to build and run the application ?

1. Download the code from the github and run the following command

		mvn clean package

2. Run the following command to start the service

		java -jar target\noughtsandcrosses-1.0.0.jar

## REST Endpoint Definition:

### 1. To start the game

	This endpoint creates the game.

		Method : POST

		URL : http://localhost:8080/ncservice/games
	

### 2. To play a game
	
	This endpoint allows the players to play the game with the gameId obtained from the start game service. First 
	player play will be marked with Cross and the reponse will show whose has to play next. Game can be continued 
	until there is a win or draw. Result will be shown in the response when the player won or draw.
	
 		Method: PUT

		URL : http://localhost:8080/ncservice/games/{gameId}
	
		Sample Request Payload:
	
			{
				"rowId":1,
			
				"columnId":2
			}


### 3. To find the result of the game

	This endpoint allows the players to find the result of the game at any stage.
	
		Method: GET

		URL : http://localhost:8080/ncservice/games/{gameId}
	
	
## Console

	The game board and the player marks can be seen in the console during the game play.
