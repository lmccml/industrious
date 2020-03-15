package book.Java系统性能优化源代码.string.tree;

import java.util.HashMap;
import java.util.Map;

public class Node {

  //子节点
  private Map<Character, Node> nextNodes = new HashMap<>();

  public void addNextNode(Character key, Node node){
    nextNodes.put(key,node);
  }

  public Node getNextNode(Character key){
    return nextNodes.get(key);
  }

  public boolean isLastCharacter(){
    return nextNodes.isEmpty();
  }

}
