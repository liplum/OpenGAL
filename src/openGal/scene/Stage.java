package openGal.scene;

import openGal.tree.StoryNode;
import openGal.tree.StoryTree;

import java.util.ArrayList;

public abstract class Stage{
  protected StoryTree story;
  protected Background background;
  protected Dialog dialog;

  protected Role binding;

  protected ArrayList<Role> roles = new ArrayList<>();

  public void draw(){
    background.draw();
    for(Role role: roles){
      if(role != binding) role.drawBackground();
    }
    if(binding != null) binding.draw();
    dialog.draw();
  }

  public void doNextAction(){
    StoryNode node = story.next();
    node.handleBackGround(background);
    node.handleRole(binding);
    node.handleDialog(dialog);
    node.action(this);
  }

  public void bind(Role target){
    binding = target;
    binding.handleDialog(dialog);
  }
}
