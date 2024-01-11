package net.geronimus.multiconf.graphics

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.stage.Stage

object UiLauncher:

  def main( args : Array[ String ] ) =
    Application.launch( classOf[ Ui ], args : _* )

class Ui extends Application:

  def start( primaryStage : Stage ) = 
    
    primaryStage.setTitle( "Hello, JavaFx!" )

    val root = new StackPane
    root.getChildren.add( new Label( "Hello, World!" ) )

    primaryStage.setScene( new Scene( root, 300, 300 ) )
    primaryStage.show()

