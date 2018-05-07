CompSci 308: Team Review
===================

## Team
 
### Team Work Environment

[ Focus on how effective you think this team experience was for everyone and what you think made it more/less effective. ]

This team experience was, for the most part, effective for everyone because, unlike other projects, everyone worked in small groups rather than as individuals. As a result, even though division of labor occurred, a small bug or uncertainty in implementation was never something you would have to resolve by yourself. Coding happened much faster, something I attribute to the lack of hard obstacles. 

In addition, a big part of the project was, obviously, integration. As a consequence of having so many moving parts, inter-team meetings were relatively frequent (to ensure that each team would interface properly), and the crossover of ideas was easy and quick. Accountability was also easily enforced, as one lagging part directly caused everything else to essentially grind to a halt, especially later in the project. 

Another large advantage to working in pairs was the ability to make meetings. For example, conflicting schedules in other teams often made teamwork difficult. However, with multi-person subteams, only one member of each subteam needs to be there, and very rarely were we unable to schedule a meeting when needed. 

Slack also became a relatively critical communication tool. It helped keep projects and issues organized, and being in multiple slack channels allowed for inter-team communication as a team-to-team, rather than as member-to-member. The one thing that I thought was somewhat less effective in this project would be integration, though in my opinion, because integration and testing by nature needs to happen on a single computer, this issue arises less out of having subteams and more out of having a lot of moving parts, something that is inherent to the nature of this project. 

Additionally, answer the following questions:
#### What were your team's goals for the project and do you think they were clear to everyone?
My team's goal was relatively clear for everyone. Something I've been really pushing to work on is interfacing. Specifically, having a couple public methods, as few as possible, for other parts of the project to reference Game Data with. Everything else in the Game Data part of the project would be hidden from every other part of the project. 

To that end, the absolute minimum number of public methods exist. For the Authoring Environment's saving, they simply have a single method to access, saveData, with parameters representing all the data structures they would like to save. For instance, the Game Engine Object they wish to save, the string representing the game folder and name (to allow the author to specify a specific file path), and maps representing metadata (with two types necessary) were passed and all saved in the back end. Game Player has a similar API, with only one method (also named saveData) that takes in a different set of parameters. In this case, because some aspects of the game should not be edited by the player, only a Game Engine Object (representing the current state of the game) and a string to name the file are passed. In the backend, special file paths are created for those files saved by the player, in order to differentiate between a "clean" version of the game and a "dirty" or played version of the game. 

For loading, it was necessary to have more methods simply because a different number of data structures needed to be returned. To that end, methods to loadData (for engine, takes in a string representing a file path), openMeta (for flexibile metadata, more info found in Analysis), and openConfig (for inflexible metadata, more info found in Analysis), were all needed by both the player and the authoring environment. Aside from these methods however, no methods were made public for anyone to see, aside from the backend of Game Data. 
#### How was work divided among the team members and do you think everyone was clear about their responsibilities?
Within my team, work was divided unclearly, simply because it didn't make a lot of sense to divide it from the outset. For the initial parts of the project, Harry and I pair-coded the game data back end, and as a pair, I feel like we both contributed equally. Later on, scheduling and other commitments caused me to take on a significant portion of the project, and I ended up handling the majority of the coding of file hierarchy, separate save files, and other features. Harry did some cleaning/refactoring of the code, but his biggest contribution was to test what I wrote to make sure it worked locally, and to teach me how to test code, since I am unfortunately quite inexperienced in creating local test cases. I handled essentially all of integration testing and bug fixing, as well as error handling and creation/changing of new features for the other teams, especially with the design and interfacing of metadata and later configuration data, all of which I was able to test myself using the skills I learned while pairwise coding (mostly watching) Harry write test cases. During this time, Harry worked on cloud integration and potentially multiplayer, but as time became short, all we had time to do was set up a Firebase connection, as creation of multiplayer's infrastructure was completed too close to the deadline for engine to adapt to it. 

#### How was the communication within the team (was it too much, too little, just right) and why?
Communication started off really well, as we met quite often and happened to be in a lull in terms of workload initially. As time went on, however, other commitments decreased the meeting times, which, in my opinion, was a good thing, as I learned better (although significantly slower) working alone. When Harry began working on his Firebase/online project and I began focusing on testing, integration, and file hierarchy, communication fell significantly, especially since it was not entirely necessary anymore; our work was in two separate areas. In fact, I built some features and made some design decisions without consulting Harry, most importantly the metadata/config data decision, because it sort of became unnecessary and something I could handle alone (something that extended towards inter-team work). A very similar case can be made for my work with Firebase; I essentially did nothing towards that project, effectively just commmenting at different points asking how stuff worked. 
#### How was clear was the work flow of committing code and how easy did it make sharing progress and why?
The workflow of committing code was relatively good in that it tracks relatively well our progress throughout the project. The biggest challenge, from my perspective, is that we would do all the testing locally. This meant that, once we were "satisfied" with a certain version of the code, we would pull and test it locally until something went wrong. Because this was not always done together, our solutions often conflicted and the best decision was often to abandon a locally pulled "solution" in favor of another. 

Sharing progress, however, was relatively easy especially with the constant integration checks and the incremental work we did with data. In other words, because we would build data pieces of a time and test those pieces locally, fix them, and continue. For example, once a version of saving a list of levels was built, we tested it locally (with test classes) to see and ensure that everything was preserved properly, and then I would meet with the other teams (in this case, engine) to create test cases and examples for saving. This way, we were constantly integrating with the rest of the team, and there were few "big" nights of integration between data and other groups. Another contributing factor to this was the simple API that Game Data adopted. 

### Rate Your Teammates

Rate your fellow students as team mates on the following scale:

 * -5 (no contribution)
 *  0 (not enough information to rate)
 * +5 (amazing team leader/designer)

| Student NetID    | Rating          |
| ---------------- | --------------- |
| Kevin Deng ktd9  | 3  |
| Andy Nguyen aln20  | 3  |
| Jennifer Chin	jrc81  | 2  |
| Elizabeth Shulman	ers58  | 1  |
| Harry Wang	hyw2  | 2  |
| Dana Park	dp178  | 3  |
| Brandon Dalla Rosa	bkd8  | 1  |
| Liam Pulsifer	lp139  | 4  |
| Jeremy Chen	jc587  | 2  |
| Ben Hubsch	bah37  | 5  |

#### For any team mate you rated a 3 or higher or lower than 0, provide specific details for your evaluation

Focus on people's roles/actions rather than their personal qualities and provide specific examples of those actions.

 * Bad Example Answer
   "Writes bad code" or "Never listens to other people"
   
 * Good Example Answer
   "Look at commit XXX, it was three days after it was promised (in an email/chat or a public meeting) and it did not work with the current version of the code so it broke the build."
   
| Student NetID    | Reasoning          |
| ---------------- | --------------- |
| Kevin Deng ktd9  | While working with Kevin, he always was on top of the work he was doing. Looking at his commit history, it's clear that he's contributing to the project consistently, and more importantly during the meetings we had, he always contributed and made many good observations, critiques, and suggestions.  |
| Andy Nguyen aln20  | Andy was much the same as Kevin. In addition to all the comments I've made on Kevin's contribution, Andy was the one who always helped me with any catching up I needed to do with Engine, or any new developments that happened since the last Engine meeting that I sat in on. Without Andy, it would likely be difficult to set up testing as well as we did, and integration would have been difficult.  |
| Dana Park	dp178  | Dana was my primary interface with the Player, and to that end she did her job very well. There was only one instance where she could not meet due to a time conflict, and, as it turns out, that time conflict was an integration meeting with another part of the project. Whenever I needed to add a new feature or method for her to access, she was always very free to discuss and to nail it down.  |
| Liam Pulsifer	lp139  | Liam is one of the hardest workers I know on this team, and on our Slogo project. While I did not interact with Liam that much on this project, while I was discussing some of the new ways to save and the requirements for loading in the Authoring Environment, he always had clear answers and, looking at his commit history and while talking to him, it's clear that the design and architecture of the Authoring Environment is his  
| Ben Hubsch	bah37  | Ben is just the goat. I credit the majority of the engine's architecture and design to Ben, and Ben put in a huge amount of work into the development of this project. He always pushed the schedule as fast as possible, and always made time for the group to meet. He also has the most consistent and constant work on this project, even while being loaded with other work.   |

## Personal

### My Rating

[ Rate yourself on the same scale as your team mates. Justify your score with specific examples to support your rating. ]
3

I think that, while I may not be the most talented coder on the team, I was very committed to working together on the project and being involved in the rest of the team's work. For example, I sat in on the majority of the Engine's meetings, and contributed what I could to their discussions. In terms of the work I've done, aside from the data saving, I tried to implement my code in the easiest way for both Player and Game Authoring to use, and to that end I think I've been relatively sucessful. Whenever there were integration meetings, I was there for Data integration and testing, and I made sure that any code that everyone else tried to integrate was as fully locally tested as possible, and in some cases I wrote test cases for partial implementations to minimize headache for the other teams. 

### My Role on the Team

[ Describe both your contributions to the project as a whole (such as implementer, designer, facilitator between team mates, debugger) and the code you worked on (such as configuration, or JavaFX or XML expert, or generalist (helped out all over, refactor many pieces of code, etc.)) ]
I was an implementer and designer for the data part of the project, with the method design coming from me. The ManipData class was mostly pair-coded, but the majority of the debugging and all of the integration and feature-adding was done by me. To that end, I worked as a facilitator beteween team mates, ensuring that my code was properly integrated and still funcitonal at every step and change. It became somewhat annoying later on, as small parameter changes were made, but I did my best to keep it clean and consistent. I also helped debug other people's code, and to find the source of errors whenever I could (or present them the solutions I made when we bump into the same errors).


## Summary

### My biggest strength as a team member

* Bad Example Answer 
"My biggest strength is leadership.  I'm a really good leader and it always seems to come easy to me.  I like being a leader; it just feels natural."

* Good Example Answer
"I enjoy being in a leadership position.  When we were choosing tasks for the GUI, I found myself noticing who had talked well about specific parts and helped to organize the whole team to work together to get the tasks they were best suited to, not just the ones that seemed most cool."

I enjoy being in a position to help integrate our code. Because a large bulk of our time was spent making sure our pieces worked together, I found myself helping out in that regard and helping test and debug. Because I understood my code completely and knew the shortcomings, whenever we would run into a bug (even if we were working on integrating something entirely different) with the data, I could usually figure out the source relatively soon. For example, at one point Jenny was running into a problem with my meta data saving, saying that she was getting null pointer exceptions, even when she gave default values for the map to save. To me, it was clear that, chances are, a string was not being passed properly to be saved and that the null pointer was trying to access that string; turns out, Jenny forgot to write a default image file path for the thumbnail in the configuration map. Towards the end, because the bulk of the data design is my code, I became the primary point of interaction and discussion regarding data design and integration. 

### My biggest weakness as a team member
I tend to fall short when it comes to communication, as sometimes I end up making unilateral design choices or changes without really consulting other teams first, especially with the backend. For example, the file folder hierarchy (games/GameName/game.xml, games/GameName/savedGames/file1.xml, etc) was a decision I made alone, and sort of forced everyone else to conform to. In addition, sometimes my design decisions were not the best, especially regarding the saving of configuration data, but working with Dana and Jenny, we created a better version of that. 

### How I improved as a team mate this semester
I think the biggest improvement I've made is in terms of communication (though it still lacks a bit) and code production. Initially a ton of time would be spent coding actively as a big team, and I was not very good at producing consistent code, with a lot of the code I pushed being buggy and lackluster. This final project especially I tried my best to only push what I viewed as good code and "verified" code for integration. 