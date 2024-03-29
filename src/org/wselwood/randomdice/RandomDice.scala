package org.wselwood.randomdice

import gui.DiceController
import javafx.application.Application
import javafx.event.EventHandler
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.stage.{Stage, WindowEvent}
import javafx.scene.Scene
import org.wselwood.common.gui.SceneLoader

/**
 * Main set up of the random dice gui
 * User: wselwood
 * Date: 18/05/12
 * Time: 18:37
 *
 * The object just starts the application and has a few bits of default state.
 */
object RandomDice {

    val title = "Dice"

    def main(args: Array[String]) {
        Application.launch(classOf[RandomDice], args: _*) // have to use :_* to pass the array through to the var args
    }
}

// Just sets up the stage. Has to be a class as it is instantiated by the Application.launch above.
class RandomDice extends Application {
    override def start(primaryStage: Stage) {

        // Initial setup of the stage
        primaryStage.setTitle(RandomDice.title)
        // In this case this shouldn't be re-sizable.
        primaryStage.setResizable(false)
		
        // Set a few handlers that can be useful. Remove if you don't need them.
        primaryStage.setOnHiding(new ShutdownHandler())
        // Disabled as we have stopped resizing
        //primaryStage.heightProperty().addListener(new WindowSizeListener("window.Height", primaryStage))
        //primaryStage.widthProperty().addListener(new WindowSizeListener("window.Width", primaryStage))

        // Actually load our scene. As long as your controller and fxml file are in the same place this should work fine.
        // The second parameter is to the properties file for localization.
        val loaded = SceneLoader[DiceController](classOf[DiceController].getResource("Dice.fxml"), "org.wselwood.randomdice.gui.Dice")

        // Create the scene from the one we loaded.
        val scene = new Scene(loaded.parent)

        // Attach to the stage and show the stage
        primaryStage.setScene(scene)
        primaryStage.show()
    }
}

// Event handlers for shut down and window size.
class ShutdownHandler extends EventHandler[WindowEvent] {
    def handle(event: WindowEvent) {

    }
}

// there is some fun here due to the differences in the class hierarchy between java and Scala. Mainly there is no Number.
class WindowSizeListener(val attribute: String, stage: Stage) extends ChangeListener[Number] {
    def changed(value: ObservableValue[_ <: Number], old: Number, updated: Number) {

    }
}
