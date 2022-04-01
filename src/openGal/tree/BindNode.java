package openGal.tree;

import openGal.scene.Role;
import openGal.scene.Stage;

public class BindNode implements StoryNode{
  public Role target;

  @Override
  public void action(Stage stage){
    stage.bind(target);
    stage.doNextAction();
  }
}
