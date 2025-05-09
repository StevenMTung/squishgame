![Squish Game Image](https://github.com/user-attachments/assets/7d98d66f-b258-4da2-9d12-a945b3e89631)

<h1>Project Description</h1>

The **Squish Game** is a game inspired by the Squid Game series! <br>
The game involves user interaction with the phone's front camera, using **Google ML Kit's Pose Detection API** to detect body movements. <br>

<p>To play, the user needs to move their arms in front of the camera as if they were running, so that their character moves from one end of the screen to the other.</p>  
While the player tries to reach the final goal, there is a doll that alternates between singing and watching the player. <br>  
According to the game rules, the user is only allowed to move while the doll is singing.  
If any movement is detected when the doll stops singing and turns to look at the player, the character is eliminated and the game ends. <br>  
The objective of the game is to move the character from one end of the screen to the other without being eliminated by the doll.


<h1>Features</h1>

- `Game Instructions`:

![Image](https://github.com/user-attachments/assets/87d409d6-4650-43ad-b08e-c0136d970b3a)

- `Score board of the best players`:

![Image](https://github.com/user-attachments/assets/654df07a-8a95-4530-a48c-35c7808d1428)

<h1>Gameplay</h1>

- `To move the character, you need to move your arms as if you were running`:

 ![Image](https://github.com/user-attachments/assets/7af7cc43-a137-469d-b31f-d8c978c06fb4)
 
- `It is forbidden to move when the doll stops singing and turns to observe the character. If movement is detected, the player is eliminated`:

![Image](https://github.com/user-attachments/assets/fcb36b38-86f3-4ccb-9b5b-2c5abe417d64)

- `To win the match, simply move the character from one end of the screen to the other without being eliminated`:

![Image](https://github.com/user-attachments/assets/94af3e5a-811a-4680-a58d-85d528f4acb6)  

- `If the player is fast enough, they make it into the Top 5 best players list`:

![Image](https://github.com/user-attachments/assets/f39344a3-bee6-49a1-bf73-0b151cee8a42)
 
 <h1>Techniques and technologies used.</h1>

The app was developed with the following technologies:

- `Dagger-Hilt`: Dependecy Injection
- `Jetpack Compose`: User interface implementation
- `ViewModel e uiState`: Screen state management
- `Navigation com NavHost`:  Navigation between screens via graphs hosted in a NavHost
- `Coroutines e Flow`:  Running operations asynchronously and reactively
- `Room`: Persistent data storage within the game.
- `ML Kit Pose Detection API`: Detect and identify points of the human body
- `CameraX`: Access to the device's camera
- `MVVM`: Model-View-ViewModel architecture pattern

<h1>Access to the Project</h1>

You can access the [project source code](https://github.com/StevenMTung/squishgame) or [download it](https://github.com/StevenMTung/squishgame/archive/refs/heads/main.zip).

<h1>Open and Run the Project</h1> 

After downloading the project, you can open it with `Android Studio`. To do this, on the launcher screen click on:

- `Open an Existing Project` (or a similar option);
- Find the location where the project is and select it (If the project was downloaded as a zip, you will need to extract it before locating it);
- Finally, click `OK`.

`Android Studio` will execute some *Gradle* tasks to set up the project, wait for them to finish. Once the tasks are completed, you can run the app 🏆 

<h1>Author</h1>

 [<img loading="lazy" src="https://avatars.githubusercontent.com/u/134224337?v=4" width=115><br><sub>Steven Marc Tung</sub>](https://github.com/StevenMTung)
| :---: | 
