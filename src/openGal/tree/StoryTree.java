package openGal.tree;

import openGal.scene.Stage;

import java.util.ArrayList;

public class StoryTree{
  private ArrayList<StoryNode> nodes = new ArrayList<>();
  private int index;

  public StoryNode next(){
    return nodes.get(index++);
  }

  public boolean isFinished(){
    return index >= nodes.size();
  }
}
