CompSci 308: Game Analysis
===================

> This is the link to the assignment: [Game](http://www.cs.duke.edu/courses/compsci308/current/assign/01_game/)

Project Journal
=======

### Time Review

* Reading Start date: 1/15, Start date: 1/16, End date: Initial completion 1/19, tweaks continued to be made afterward. Total hours: 25

* Time Breakdown (Hrs): 
> - Coding new features: 8
> Initially, the time spent coding new features was not necessarily coding new features but rather implementing the basic functionality of the game. These functionalities include things like walls, balls, bricks, paddles, and constructors/classes for each of these. Eventually, addition of new features, such as cheat codes, scoreboards, a "debugging mode", and different block types consumed the majority of the time spent coding new features. For example, the biggest challenge was the explosive block, due to extensive roadblocks in space and time efficiency. More specifically, I wanted to avoid using a 2D array as a structure because it's clunky, but in order to preserve relative positions, I would have to create sub groups for each instance of the explosive block. When playing with that idea, however, I hit the block that I would still need a way to retrieve adjacent blocks, and ultimately gave in to the 2D array design. This feature hands-down took the most time, though the actual implementation did not.
> - Refactoring: 1 
> Refactoring should have been a much more significant portion of my project had I understood abstract classes and inheritance. As part of my masterpiece, I refactored the entirety of the Brick-Collision computation, as well as the way blocks are generated. However, in the original code, the only minor refactoring I did was moving methods into classes to reduce the amount of coding I might need to do. For example, making "score" a parameter of ball was far more intelligent of a design that to have the overarching management class run "score" because there is less confusion with who earned which points if it becomes the ball's that makes contact.  
> - Testing: 10
> Testing formed a very significant part of my time consumed. Though some things such as paddle bounces and wall bounces were consistent and predictable, every other interaction always had some sort of wonky effect. For example, bouncing off of blocks was extremely difficult for me to code around, due to complex hit-boxes. Because several booleans formed rather complex boolean expressions, errors were difficult to detect and the only symptoms I could see were "well that was a weird interaction but the ball was moving too fast for me to understand what was going on". Testing in this case included multiple launch angles and multiple launch locations to test the hitboxes. Interesting interactions occurred when, for example, two hits were detected almost simultaneously or even  concurrently and the ball would reverse direction twice in extremely quick succession, creating the impression that it simply "ghosted" through the blocks. 
> Another aspect that required very extensive testing was the implementation of the effects of each block. This is partly due to the design choice of making every block visually identical. Thankfully, this testing was relatively simple by simply spawning, for every block except for God Block, a matrix of all one type of block and observing the effects. For the God Block, the testing was simple after the implementation of "debugging/invincibility mode", which meant I could just watch until the God Block disappeared.
> - Reading: 1
> Not a whole lot to say here. I skimmed the provided resources, but not thoroughly enough to understand many of the concepts, and decided to jump right into programming. In retrospect, probably would have saved me time to read more carefully.
> - Designing: 4
> In setting up the framework of my project, I essentially created a very basic outline of what methods I might need and what parameters my objects needed to have. Because not a lot of foresight went into this planning process, I ended up with some convoluted methods. Had a better plan been created, I would not have had complex code and could have better followed OOP. Segmentation and modularity could be greatly improved and debugging much simpler. I would like to point out, however, that I did have the foresight to add certain methods and parameters to my Object classes, though many at the end of the day were not used in favor of a better implementation.
> - Debugging: 10 (Overlaps with Testing)
> See Testing
> - Documenting: 1
> Among the last of my commits, Documentation was done rather cursorily due to not really understanding what documentation meant aside from describing method and class functions. As such, documentation might be a bit lacking
> - Meeting with Team Members
I had the great fortune to be able to meet with all my Team Members whenever I wanted. After all, no schedule conflicts can occur with a team of one.
* My first significant commit happened once I had a functioning game. In other words, I first added, committed, and pushed once my ball was detecting bricks (bounces were still wonky), controls and some special features were implemented. Every time I made significant improvement I committed and pushed (e.g. I plan on fixing bouncing and collision reaction; once done, sync with Git). For me, however, because "milestones" were really far apart, not a lot of commits were made. Looking through my history, my first commit represents completed framework and essentially completed but buggy code. My second commit is almost completely functional but messy code. My third commit was adding comments and README file, and the remaining commits were simply cleaning up code. 

* I frequently developed and tested code as I went, though initially no code was tested simply because testing was not really effectual. For example, no testing is really necessary for my implementation of Ball; there's not exactly a lot there to test. However, as I got further into the project, around the time of having a ball and paddle appear and interact on screen, I began to test as I went, at least cursorily, to make sure nothing was catastrophically wrong. At one point, however, I had hit a major snag with syntax that required an entire TA's session to resolve. In the little more than a day between hitting the snag and being able to meet a TA (due to snow), I decided to ignore the error and assume it worked, and essentially finish the rest of the project. In that time, I built several methods that assumed my parsefile worked, but could not test because parsefile did not work, and made very significant progress, to the point that once that syntax error was resolved, I was nearly done with the rough functional draft of the game. Once I had a functional game, any changes I made, once completed through, was tested to see how it impacted the game, and to see whether it had the intended functionality. 

* I honestly really enjoyed this project a lot. I think the hardest part for me was that I constantly tried to think of the most efficient ways to do things. For example, I tested many implementations of my "explosive" block, which was problematic because it requires me to somehow save block relative locations. It seems extremely inefficient to initialize and entire 2D array of blocks and iterate across it in order to preserve location, but the implementations of additional sub-groups and children and linked lists got convoluted and much less intuitive, so after significant testing I reverted back to the 2D array. I also ran into some hitbox difficulty, mainly because it's not natural to think of "down" as "increasing y", and that wreaked havoc with my hitbox calculations. By and large the easiest, though most definitely not the cleanest, part was simply creating the ball and paddle classes, because those did not have any special parameters beyond what was expected. I think the best use of my time so far was actually this reflection. I learned more about how to actually implement inheritance in an effective manner and it's so much cleaner. 

### Commits

* I committed a total of 6 times. The average size isn't significant because, aside from the first commit, the rest of my commits were usually just tweaks or debugging changes. More specifically, my first code commit was approximately 1000 insertions, but every other commit after that hovered between 100 and 300 total insertions/deletions. Though more consistent committing would have been much better, for me as a person, I have a preference to only commit when I feel I have something commit-worthy, when I have accomplished something significant. Unfortunately, those accomplishments tend to be few and far between, and hence the infrequent commits and pushes. 

* I do not feel like they accurately represent the "story" because there's no swimming pool filled with tears in that repo.
> - In all actuality , it's not really representative because I didn't commit often enough to illustrate the history well. One moment there's nothing, next there's a ton of stuff. As a result, very little of the developmental history of this project exists; it's as though on the first day God created light and all of a sudden a week later Eve eats the apple. However, from the commit messages (aside from the first one), they all accurately represent what was done between each commit. I'm actually quite proud of how my commit messages detail what was changed at a conceptual level, despite the rarity of such messages.

* "Update 1": 
This was the first commit I made, and it essentially laid out the framework for me to fix. At this point, essentially everything I would need, minus some additional features, had been implemented to some degree. This was my big "nothing to everything" commit. This was one "packaged" commit because it represented everything I had done up to this point; I had reached my first milestone. Unfortunately, the nature of such a big milestone makes it such that it is definitely not reasonable to expect someone to understand completely what's going on; there's simply too much going on. 

* "Cleaned up and commented, removed magic numbers. Some classes are not used, those have been left in their potentially broken state. can delete if necessary": 
As the commit message says, this was my first version of a "completed game". Most bugs had been sorted out, the game became playable. Features like cheat codes were implemented, and there was selective usage of "magic numbers" that I fixed. This was packaged this way because it essentially represents a rough, pre-release, beta edition of my game. Again, the differences between this commit and the last are quite significant and difficult to follow (by nature of having one big management class), but more manageable than Update 1.

### Conclusions
* I think I underestimated the size of this project, but I also planned on getting it done early at any cost (had a busy weekend scheduled), so as I realized the workload, I consequently was able to adjust my schedule to work around it. My time management, for the most part, was pretty well done. I think this system works pretty well for any class; if you assume the due date is two days earlier than it is, and something comes up, you're still in the clear because you were ahead in the first place. It allows you to be flexible. 

* My hitboxes and my method calls needed a ton of editing due to poor prediction of the variables needed and the messy design I had committed to. After learning more about inheritance, it made my code so much cleaner and neater. I think this results from a too dogmatic following of my initial design method; too many parts of it were inefficient and required workarounds that didn't work. As a result, significantly greater debugging was needed to adapt. 

* Planning more would be very helpful, as well as being far more flexible in creating new classes and methods. Also, abstract classes and inheritance is awesome and would make my life so much easier. I had thought about using abstract classes initially, but I didn't understand them enough to implement them, and switch cases/extended if statements were familiar, if messy. 

* Given the opportunity, I'd replace my entire collision and brick generation segments of my code with OOP-versions of them, the versions in my master piece. It's just so cool how clean it makes it and easier to debug it makes it. For more specificity, see my Masterpiece.


Design Review
=======

### Status

* My code started off pretty consistent, but as things started getting added the order of some functions has changed in such a way as to still be readable, compilable, and functional, but somewhat less intuitive. However, the naming conventions and overall readability, I feel, are well done and clear; no "random" or temporary variables are used, and constants are aptly named. 

* The code is readable in that functions and methods are well named, and parameters make sense. Nothing is random or unclear as to it's intent, and naming conventions were pretty consistent. The only non-readable parts of the original code are some of the boolean expressions, because they require mathematics and manipulations that are hard to immediately visualize and verify. 

* Many of the dependencies in my code are, in my opinion, clear, though a significant portion of my variables are global variables. I would argue, however, that the globalization of some variables, such as "root", makes it easier to access and leverage. 

```java
    /**
     * Computes the effects of brick-collision
     */
         private void BrickCollide(Ball B) {
    	for (int i = 0; i < map.length; i++) {
    		for (int j = 0; j < map.length; j++) {
    			Shape intersect1 = Shape.intersect(map[i][j], B);
    			if (intersect1.getBoundsInLocal().getWidth() != -1) {
    				if ((map[i][j].Exists) && (B.getCenterY() > map[i][j].getY() && B.getCenterY() < map[i][j].getY() + BRICK_L) && ((B.getCenterX() > map[i][j].getX() - BALL_RADIUS) || (B.getCenterX() < (map[i][j].getX() + BRICK_W + BALL_RADIUS)))) {
    					B.xVel = -1*B.xVel;
    					brickhit(map[i][j], B, i, j);
    					updatescore();
    				}
    				else if ((map[i][j].Exists) && ((B.getCenterY() > map[i][j].getY() - BALL_RADIUS) || (B.getCenterY() < (map[i][j].getY() + BRICK_L + BALL_RADIUS)))) { 
    					B.yVel = -1*B.yVel;
    					brickhit(map[i][j], B, i, j);
    					updatescore();
    				}
    				if ((map[i][j].ID != 1) && (map[i][j].ID != 6)) {map[i][j].destroy();}
    			}
    		}
    	}
    }
```
> - This code functions to iterate across my 2D array of blocks, checking each one to see if contact between it and a ball has been made, and, if contact is made, where it is made and what effect that has. This code from a conceptual sense is not terribly difficult to understand, but the difficulty lies in the train-wreck of a boolean expression. The boolean expression serves to determine where the ball is making contact with the brick using relative positioning of the ball to the brick. Though complex, because it only exists in one spot in the entirety of this code, it didn't make much sense to make a separate boolean method, though using inheritance it's possible to make this look much cleaner (part of my masterpiece). 

```java
    /**
     * Handles user input
     */
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.S) {myMover1.setY(myMover1.getY() + MOVER_SPEED);}
        else if (code == KeyCode.W) {myMover1.setY(myMover1.getY() - MOVER_SPEED);}
        if (code == KeyCode.DOWN) {myMover2.setY(myMover2.getY() + MOVER_SPEED);}
        else if (code == KeyCode.UP) {myMover2.setY(myMover2.getY() - MOVER_SPEED);}
        if (code == KeyCode.Q) {System.exit(0);}
        if (code == KeyCode.I) {
        	myMover1.setHeight(SIZE);
        	myMover2.setHeight(SIZE);
        	myMover1.setY(0);
        	myMover2.setY(0);}        
        if (code == KeyCode.DIGIT1) {myBouncer1.score = myBouncer1.score + BONUSPOINT;}
        if (code == KeyCode.DIGIT2) {myBouncer2.score = myBouncer2.score + BONUSPOINT;}
        if (code == KeyCode.P) {myBouncer2.xVel = 0; myBouncer2.yVel = 0;}
        if (code == KeyCode.O) {myBouncer2.xVel = BALL_XVEL; myBouncer2.yVel = BALL_YVEL;}
        if (code == KeyCode.C) {myBouncer1.xVel = -myBouncer1.xVel; myBouncer1.yVel = -myBouncer1.yVel;}
    }  
```
> - This code functions as what is essentially a large switch case. It handles the users' input, changing score and velocity among other things to enact the instructions of the user. It's easy to understand and read because its virtually just a switch case; if key is K, then do what K does. The naming conventions and the simplicity of this programming style make it almost universally comprehendable. 

### Design

The first thing that my code does is import a lot of libraries. This is necessary because a lot of the functions and methods called by the program are defined by these libraries. These libraries include:
> - javafx.animation.KeyFrame;
> - javafx.animation.Timeline;
> - javafx.application.Application;
> - javafx.event.ActionEvent;
> - javafx.geometry.Insets;
> - javafx.scene.Group;
> - javafx.scene.Scene;
> - javafx.scene.control.Label;
> - javafx.scene.image.Image;
> - javafx.scene.control.Button;
> - javafx.scene.input.KeyCode;
> - javafx.scene.layout.VBox;
> - javafx.scene.paint.Color;
> - javafx.scene.paint.ImagePattern;
> - javafx.scene.paint.Paint;
> - javafx.scene.shape.Rectangle;
> - javafx.scene.shape.Shape;
> - javafx.scene.text.Font;
> - javafx.scene.text.FontWeight;
> - javafx.scene.text.Text;
> - javafx.stage.Stage;
> - javafx.util.Duration;
> - java.io.*;

After that, within the class itself, I define a multitude of self-explanatory constants. These constants include things from ball speed to spawn location to colors and padding. They are declared as "public static final" because these constants should not change throughout the program. Then, I declare several global variables that I want every method to be able to access and change. These include:
> -  Text Score_Text1;
> -  int score1;
> -  Text Score_Text2;
> -  int score2;
> -  Scene myScene;
> -  Paddle myMover1, myMover2;
> -  Ball myBouncer1, myBouncer2;
> -  StdBlock BrickBuffer;
> -  Group root;
> -  StdBlock[][] map;
> -  Scene splash;
> -  String LevelFile;
> -  Button btn1;
> -  Button btn2;
> -  Button btn3;
> -  Button btn4;
> -  Stage thestage;
> -  int MOVER_SPEED;
> -  Rectangle EndScreen1;
> -  Rectangle EndScreen2;

Then, once main is executed, the method "start" is launched and run. Start sets up the window layout, and follows the rules defined by setupSplash. SetupSplash uses the VBox structure to build the menu screen. It adds text to the menu screen for user perusal, and four buttons, each of which loads a specific matrix file to be read as the "map" of the game. 

In order to load that map, the method "ButtonClicked" determines which button was pressed and sets the variable "LevelFile" to the corresponding filepath. Then, it calls setupGame, which, as the name implies, sets up the game. This method also contains the code that controls animation and the "step" function
> - The step function is cruical to the operation of this entire game. It moves the objects by refreshing every frame, adjusting the locations of the objects with built-in methods like "getX()". It then computes whether contact has been made with a paddle or the opposing wall, and, if true, switches the sign of the x-component of velocity. One of the end-game conditions is implemented here; if the ball's x-location exceeds the bounds it is permitted, then the game will call either p1wins or p2wins. 
p1wins and p2wins have functionally the same code, just with a different filepath. They simply load an image, set a rectangle's fill to that image, and have that rectangle fill up the entire screen. In addition, they set the velocities of both balls to zero to enact a game stop. 
> - After the endgame conditions, step then computes whether a brick-ball collision has occurred by calling another method called BrickCollide.
BrickCollide handles all collisions. It iterates across the 2D array of blocks, checking if an intersection has occurred. If it has, it then checks whether that brick's parameter "Exists" is true or false. If true, then BrickCollide updates ball speed and score, and calls brickhit, which implements the effects of each block. Brickhit's purpose is to change all the aspects of the visual playing fields and to change all the underlying data structures in accordance to whatever effect is being added to the game, determined by the ID tag. 
> - Something important to note is that the second endgame condition is executed after BrickCollide; this if statement checks how many objects are left, and, if a specific number are left (representing immutable objects such as paddles and balls), then endgame() is called. 

In setupGame, the bulk of the image generation is handled. First, I initialize a Scene to add all my objects to. Then, I initialize paddles, balls, and call a method called parsefile

> - parsefile simply takes in a filepath and converts the data in that filepath into a square matrix. This assumes that the input is a square matrix, and indeed it must be as is currently implemented. 
After parsefile executes, I declare a 2D array called map of blocks. This serves to preserve the relative positions of all the blocks, for later implementation of the explosive block. A double nested for loop (that probably could have been it's own method) iterates throughout each cell of this map, adding bricks to both the map and to the "root" using another method called "GenerateBlock"
> - GenerateBlock does exactly what it's name suggests; generates blocks. It takes in 3 input integers. The first tells GenerateBlock what kind of block is being generated, denoted in the class as "ID". GenerateBlock behaves according to it's switch case. It also takes in two more ints, both of which are scaled to represent locations on the window. Certain properties are assigned to the block that is generated based on what its ID is. 

Beyond paddles, balls, and bricks, two scoreboards are also initialized and added to the screen. Finally, "handleKeyInput" is called to deal with user input. 

This class makes calls upon several other classes, each representing the objects that exist in the game. 
> Ball
> - Ball's class is a simple extension of the javafx object "circle" that adds parameters like score, x-velocity, and y-velocity. Methods exist and are used to update the speed (and consequently direction) of the ball, as well as adding points to the score. The advantage of having the ball keep track of a player's score is that there's no possibility of "losing" a score

> Paddle
> - Paddle's class is a simple extension of the javafx object "rectangle" that add the parameter velocity and the corresponding methods to change the value of the velocity or the sign of velocity (effectively reversing the direction of velocity). The advantage of having these methods inside Paddle is that it internalizes this inherent property of a paddle to a method that applies to only paddles. In addition, the naming convention is convenient; what else could "this.ChangeDir" mean besides change direction?

> Block
> - My original implementation of Block was suboptimal. Essentially, Block was an extension of Rectangle that contained parameters such as "Exists" and "isGod" (not named isGod but had that intent) and most importantly and "ID" tag. Using that ID tag, the management superclass used a combination of complex if statements and switch cases to determine what to do and how to behave depending on the tag of the block. This implementation is inherently messy because, for example, adding a new possible ID requires extremely pervasive changes throughout the code. The alternative inheritance implementation used in my Masterpiece cleans this up significantly, making the code much neater and more modular. 

So, you want to add a new level to this game? Ezpz, lemon squeezy. 
1. Create your input matrix file. Make sure it is square with size 7 (adjustable, but constants in the beginning such as brick width and padding will need to be scaled to ensure all blocks fit on screen), and that all values are between 1 and 7. 
2. Add a new button in the method "setupSplash", and edit "ButtonClicked" to set the click effect of this new button to set LevelFile = (filepath to your new input file). Make sure you make the button global.
3. You're done! Make sure you only have one God Block though, or else you will end up with a bunch of unbreakable blocks, at which point the loser becomes whoever loses their ball first. 

Throughout my code, small adjustments can easily be made to make the code much more flexible (taking a greater variety of inputs), but hands down the biggest flaw in my code is the awkward implementation of blocks and the interactions it might have. After learning more about inheritance and OOP, I can confidently say that my Masterpiece's update to the Block class would make my code far simpler and easier to both create and understand. Throughout the whole process of writing my original project, expressions and calls kept getting messier and messier, and I kept having to declare variables as globals to ensure that everything had access to them. With proper implementation of inheritance and simplification and combination of methods, both reability and debuggability are greatly improved. In terms of dependencies and assumptions, the very rigid way my code is implemented (constants and inputs must be ints, scaling must be an int, matrix must be 7x7 square, etc) and the great many dependencies of the manager glass on all the helper classes means that this code, for the most part, works exactly as it is and only as it is. Some of this can be addressed simply with better constant/variable declarations and a better "parsefile" function, but the biggest benefit is drawn from implementing OOP as done in the Masterpiece.

Feature #1: Cheat Codes
> - Classes needed: Paddle, Ball
> - Assumptions: Both player's paddles have been initialized, both player's balls have been initialized, several constants have been initialized, and that the user has a connected keyboard as input.
> - The implementation of Cheat Codes was relatively simple; upon detecting a key input, handleKeyInput effectively executes a switch case (though I implemented it as a series of if statements) to figure out which of the possible changes should be made to the game field. This code is pretty easily changed; simply add another if statement to add a new possible effect. However, a pretty severe limitation native to this method is the fact that multiple if statements cannot execute, even if no "else if" is present. Rather, if two keys are continuously pressed, Javafx registers it only as the second key being continuously pressed, and thus only parts of the switch statement can execute. 

Feature #2: God Block
> - Classes needed: Block, game
> - Assumptions: Ball has been initialized, a map of all blocks in the game has been initialized, and integer representing the number of unbreakable objects, including the God Block, group containing all objects has been initialized and properly updated
> - The implementation of the God Block is pretty straightforward; whenever contact between a ball and the God Block is made, the God Block checks to see if it is the last remaining block by checking the number of children in the group containing all currently active objects and seeing if that value is equal to the number of unbreakable objects, at which point it can perform the removal of the God Block and the addition of points to the ball that made contact with the block. A limitation exists, however, because as currently implemented there can only be one God Block present (a unique Object Count number, currently a constant, must be assigned to different levels should multiple God Blocks exist). As it currently stands, in the case of multiple God Blocks, those Blocks are rendered truly unbreakable and the loser becomes not who scores the fewer points, but who dies first. 


    

### Alternate Designs

Explosive Block
> The Explosive Block proved to be a very significant source of headache, largely due to my stubbornness to avoid a 2D array. Aside from using a 2D array to preserve position and to remove surrounding blocks by doing the common [i][j+1], [i][j-1]..., the most significant alternative method I considered was adding either pointers to the surrounding blocks into the Explosive block or making a group a parameter of the Explosive block and adding the surrounding blocks as children. Both of these ideas, however, still relied on a precursor 2D map in order to figure out what blocks surround the explosive block. The benefit to using a group or a set of pointers is that the code becomes much cleaner and block removal becomes a simple process (e.g. group.getChildren().removeAll), as well as making it possible to move the explosion method into the class of the explosive block itself. However, the cost of this idea is that a 2D map is still required to extract the surrounding blocks. A second idea or extension on the first idea is to extract the surrounding blocks using clever mathematics in the GenerateBlocks loop, using the length of the input matrix as a way to figure out the surrounding blocks. The tradeoffs here, however, revolve around the it destroys readability and will very like require more global or derived constants to ensure that the mathematics behind the extraction work. Implementation can also become quite difficult, and as it stands the complexity does not really change. While I personally prefer the method of extracting the surrounding blocks as the blocks are being added to the screen, it's difficult to argue that the method using a 2D array is harder or less intuitive. 

Block Class
> - The Block Class, as it is currently implemented, is far from optimal. Ideally, I would replace the current "StdBlock" class with an abstract class called "block", and build every block type off of that abstract class. Such a process is shown in my Masterpiece. By following rules of inheritance and OOP, we can encapsulate more of the methods and functionality in the objects themselves, thus making debugging easier and readability greater. For more details into how this would be done, examine my Masterpiece. The benefits of such a design are great, including better modularity, cleaner code, and easier comprehension, among all the additional benefits of OOP. However, the one drawback is that coding this way may require tedious syntax correction if a new feature is being added (for example, if a new block needs and int K to execute "react()", every subclass's react() method must take in int K, regardless of whether or not it is used. Despite this drawback, I far prefer the usage of inheritance and OOP, as it improves the code's flexibility immensely and makes the job of both the developer and a reader much easier. 

The three most important bugs still present in the project's design are as follows:

* Inflexibility among method inputs: The vast majority of my methods are extremely picky about your inputs, and have very little built-in adaptability to any inputs outside of the norm. For example, the method "parsefile", and every piece of code dependent on the input matrix, requires that the input matrix be a 7x7 square matrix. Anything different in size requires many constants to be scaled appropriately; anything different in shape (e.g. 6x7) requires an entirely new implementation of parsefile and a new interpretation of the matrix by the rest of the class. 
* Buggy paddle hitboxes: As has been mentioned, sometimes the hitboxes react strangely. For example, if the ball makes contact with the sides of the paddle, it will not retain it's x-velocity, instead switching it from positive to negative until it exits the paddle. This is very likely due to the lack of coding for the sides of the paddle, but should be pretty easily addressed. A more difficult to address bug relates to speed; if the ball is moving fast enough, it is possible that, in one frame, the ball might be in play, and in the next, out of play, regardless of paddle position. This means that at high enough speeds, there is no way to physically play the game, as the ball effectively teleports behind the player's paddle. A solution would be to implement a hard cap on the possible speeds of a ball. 
* Buggy brick hitbox: The two more significant hitbox issues are with bricks. Occasionally, for unknown reasons, a ball might clear two blocks but not change direction at all, and would have appeared to just pushed through the two blocks. It is most likely that this isn't a bug in the code at all, but rather an effect of the ball actually squeezing in between the two blocks and bouncing off of both, resulting in an extremely minor deflection, but with the appearance of no change. Sometimes, however, the ball may, for example, travel towards the top right corner of a brick coming from the lower right of the screen, but the game would detect the ball as hitting the top edge and reflect it down, rather than correctly as the vertical side of the brick. This case happens extremely infrequently, however, and has been difficult to ascertain as to why it occurs. Or rather, what is happening is clear (contact with the bottom of the upper boundary) but as to why the side of the block does not make contact first is unclear. 