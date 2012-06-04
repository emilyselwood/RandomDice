package org.wselwood.randomdice.gui

import javafx.fxml.FXML
import util.Random
import javafx.scene.control.{Tooltip, TextField}
import javafx.scene.paint.Color
import javafx.scene.effect.{BlurType, InnerShadow}

/**
 * Very simple controller class. No need for a model here it will only un-necessarily complicate things.
 *
 * User: Sparrow
 * Date: 18/05/12
 * Time: 18:58
 *
 */

class DiceController {

    @FXML var resultField: TextField = null
    // done result as a field rather than a label so it can be copied from.
    @FXML var minNumberBox: TextField = null
    @FXML var maxNumberBox: TextField = null

    lazy val random = new Random(System.currentTimeMillis())

    /**
     * Our only button's action method.
     * Since JavaFX2.1 we don't need any parameters.
     */
    def runButtonAction() {
        if (validateInputs()) {
            val min = minNumberBox.getText.toInt
            val difference = maxNumberBox.getText.toInt - min
            val result = random.nextInt(difference + 1) + min
            resultField.setText(result.toString)
        }
    }

    /**
     * It would be nice to have the validation happen as they type so they can only enter numbers.
     * But for now this will do.
     * @return true if validations pass, false if not.
     */
    def validateInputs(): Boolean = {
        resetValidations()
        var result = true
        if (minNumberBox.getText.isEmpty) {
            showError(minNumberBox, "Min must be supplied.")
            result = false
        }
        else if (!minNumberBox.getText.matches("[0-9]+")) {
            // hugely overkill here but proves a point
            showError(minNumberBox, "Min must be a number.")
            result = false
        }

        if (maxNumberBox.getText.isEmpty) {
            showError(maxNumberBox, "Max must be supplied.")
            result = false
        }
        else if (!maxNumberBox.getText.matches("[0-9]+")) {
            // hugely overkill here but proves a point
            showError(maxNumberBox, "Max must be a number.")
            result = false
        }

        if (result) {
            // we have two numbers
            if (minNumberBox.getText.toInt >= maxNumberBox.getText.toInt) {
                showError(minNumberBox, "Min must be a smaller number than Max.")
                showError(maxNumberBox, "Max must be a larger number than Min.")
                result = false
            }
        }
        result
    }

    /**
     * Mark the provided field as being in an error state.
     * @param field the field to mark
     * @param message the tool tip to provide it.
     */
    def showError(field: TextField, message: String) {
        if (field.getTooltip == null) {
            val innerShadow = new InnerShadow()
            innerShadow.setColor(Color.RED)
            innerShadow.setBlurType(BlurType.THREE_PASS_BOX)
            innerShadow.setRadius(14.0)
            innerShadow.setHeight(field.getHeight)
            innerShadow.setWidth(field.getWidth)
            field.setTooltip(new Tooltip(message))
            field.setEffect(innerShadow)
        }
    }

    /**
     * reset our fields back to default state.
     */
    def resetValidations() {
        minNumberBox.setTooltip(null)
        maxNumberBox.setTooltip(null)
        minNumberBox.setEffect(null)
        maxNumberBox.setEffect(null)
    }

}
