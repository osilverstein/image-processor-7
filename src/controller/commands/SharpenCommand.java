package controller.commands;

import model.ImprovedImageProcessorModel;
import model.filter.Sharpen;
import utilities.ImageRunTimeStorage;

/**
 * The SharpenCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to sharpen the image using a 5 by 5 kernel.
 */
public class SharpenCommand extends AbstractCommand {
  /**
   * Constructor to be supered by an extensions of this abstract class AbstractCommand.
   *
   * @param firstInput  is the old filename in the storage
   * @param secondInput is the new filename in the storage
   * @param map         is the ImageRunTimeStorage of the models and their String names
   * @throws IllegalArgumentException if the {@code map} is null
   */
  public SharpenCommand(String firstInput, String secondInput, ImageRunTimeStorage map)
          throws IllegalArgumentException {
    super(firstInput, secondInput, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.filter(new Sharpen());
    return this.firstInput + " sharpened. Now called: " + this.secondInput;
  }
}
