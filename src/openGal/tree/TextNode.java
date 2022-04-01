package openGal.tree;

import openGal.scene.Dialog;
import openGal.scene.Role;
import openGal.scene.Stage;

public class TextNode implements StoryNode{
  public String text;

  private Role lastRole;

  @Override
  public void action(Stage stage){
  }

  @Override
  public void handleRole(Role role){
    lastRole = role;
  }

  @Override
  public void handleDialog(Dialog dialog){
    dialog.setRole(lastRole);
    dialog.setText(text);
  }
}
