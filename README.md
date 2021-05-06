# SEIS635FinalProject

Group: 8 <br>
Class: SEIS635 Final Project <br>
Date: 05/07/2021 <br>
Member Name: Dai Le, Dang Vu Quang Dang, Ryan Poorman <br>

**1) A very quick summary of what your program does.**

This is a Tic Tac Toe games using Java code based. We can play either with computer or with other players.

**2) Special instructions, if any, for building your project. (If not, say none)**

* Before running the Tic Tac Toe will need to add on lib either from java source or from maven repo:
    * Junit 4.13.2
    * Log4J for logging purpose
    * MySQL Connector Driver for Java

**3) Project organization. Tell me where I can find everything.**

* The project source code split 2 parts:
    * Main Java Code should be under SEIS635FinalProject > src > main > java ...
    * The Junit Test Code should be under > test > java ...

**4) Program operation.  Describe what I need to do to run the program.**

* Assume all special instructions already setup correctly,
  from SEIS635FinalProject > src > main > java com.seis635.group.tictactoe > Click on **TicTacToe** and compile/run this file.


**5) Supplement information purpose is below:**

* **Database:** tic-tac-toe
  table name: infotable
  Column name: id, username, password, name, win, lose, tie
  Entry 1: Store AI data
  Entry 2: Store Guest data (if later we dicide to keep Guest record)
  Entry 3,4: Store Player1 and Player2 data in PlayerVsPlayer mode
  <br>
* **User Stories:**

  . As a user, I would like the game to have Player Vs Player mode, so that I can play the game with my friends.

  . As a user, I would like the game to have Player Vs Enemy, so that I can play against the computer.

  . As a user, I would like the game to have a login page, so that I can create my own profile.

  . As a user, I would like tha game to have a login page, so that I can log in my profile and keep track of score.

  . As a user, I would like the login page to have a exit button, so I can quit the game.

  . As a user, I would like the game to have a tracking board, so that I can know how many games I have played against AI.

  . As a user, I would like the game to track a tracking board, so that I can keep track of my wins and losses against AI.

  . As a user, I want the game to have a rematch button, so that I can play the game multiple rounds.

  . As a user, I want the game to have a guest mode, so that I can trial the game.

  . As a user, I want the game to have background music, so that I can listen while playing.

  . As a user, I want to be able to mute the game music, so that I can play the game in silent.

  . As a user, I want the game to keep tracking the scores between me and my friend, so that I can know who is winning the game.

  . As a user, I want the game to have an exit button, so that I can close the game.

  . As a user, I want the game to show me the option to continue playing or quit the game, so that I can continue to play or quit whenever I want to.

  . As a user, I want to all the databases connect through AWS Services for ease of shared access

  . As a user, I want to have validation message successfully log in to account

  . As a user, I want to have validation error message return if user unable to log in to account

**6) Credit and Features Our Team Developed:**

* This is an open source TicTacToe game, and our team have developed new features on top on this current project. Majority of our features we developed defined user stories above on this existing project as below: <br>

**7) Screenshot Sample**

![alt text](https://github.com/dang1840/SEIS635FinalProject/blob/main/screenshot/TicTacToe_Screenshot.jpg)
![alt text](https://github.com/dang1840/SEIS635FinalProject/blob/main/screenshot/TicTacToe_Screenshot_1.jpg)
![alt text](https://github.com/dang1840/SEIS635FinalProject/blob/main/screenshot/TicTacToe_Screenshot_2.jpg)
![alt text](https://github.com/dang1840/SEIS635FinalProject/blob/main/screenshot/TicTacToe_Screenshot_3.jpg)



