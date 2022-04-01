package openGal.tree;

import openGal.scene.Background;
import openGal.scene.Dialog;
import openGal.scene.Role;
import openGal.scene.Stage;

public interface StoryNode{
  default void handleRole(Role role){

  }

  default void handleBackGround(Background background){

  }

  default void handleDialog(Dialog dialog){

  }

  void action(Stage stage);
}
