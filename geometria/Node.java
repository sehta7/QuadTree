import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//class to create a tree for QuadTree algorithm
public class Node {
	
	public ArrayList<MyElement> points;
	public Node parent;
	public LinkedList<Node> children;
	
	//default constructor
	public Node() {
		parent = null;
		children = new LinkedList<Node>();
	}
	
	//constructor with parent
	public Node(Node parent) {
		this();
		this.parent = parent;
	}
	
	//constructor with parent and data
	public Node(Node parent, ArrayList<MyElement> points) {
		this(parent);
		this.points = points;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public List<MyElement> getData() {
		return points;
	}
	
	public void setData(ArrayList<MyElement> points) {
		this.points = points;
	}
	
	//makes new child
	public Node addChildren(Node child) {
		child.setParent(this);
		children.add(child);
		return child;
	}
	
	//add child with data
	public Node addChild(ArrayList<MyElement> points) {
		Node child = new Node(this, points);
		children.add(child);
		return child;
	}
	
	//get child by index
	public Node getChild(int i) {
		return children.get(i);
	}

}
