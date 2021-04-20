# MyChess
This is a simple chess game built in Java. I play a fair amount of chess and want to build my own engine. The end project will
have a PGN reader and a custom AI to play against. This program would ideally be meant for training or prepping for tournaments, etc.
## Methodology
I have tried to keep the UI as seperate as possible from the engine, similar to MVC. DRY is followed as much as possible and many
of the engine functions have been abstracted to the best of my abilities (such as the move calculator functions in Utils Class).
## Installation
I used Intellij to build this. You may need to download and attach the JavaFx modules separately to your workspace.
You will need to add the following commands to your VM Options in your Run Configurations.
* --module-path "YOU'RE PATH HERE\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml
## Libraries Used
* [JavaFx](https://gluonhq.com/products/javafx/) - GUI framework used
## Inspiration 
* [Software Architecture & Design](https://www.youtube.com/c/amir650/videos) - Youtuber whose tutorials helped jumpstart my creation of this game.
Some of my classes still look very similar to Software Archictecture & Design's classes. I am slowly changing them to be more my style.
Most are marked with TODO's.
## License
Project licensed under GNU General Public License v3.0
