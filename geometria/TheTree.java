import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class TheTree {
	public Node root;
	
	public TheTree() {
		root = null;
	}
	
	public TheTree(Node root) {
		this.root = root;
	}
	
	public static void main(String[] args) {
		/*Point p = new Point(0,0);
		Point p1 = new Point(0,80);
		Point p2 = new Point(80,0);
		Point p3 = new Point(80,80);
		ArrayList<Point> list = new ArrayList<Point>();
		list.add(p);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		Node korzen = new Node(null, list);
		int x = 80;
		int y = 80;

		Rec(korzen,x,y);
			while(x >= 20) {
				korzen = korzen.getChild(0);
				Rec(korzen,x,y);
				korzen = korzen.getParent();
				korzen = korzen.getChild(1);
				Rec(korzen,x,y);
				korzen = korzen.getParent();
				korzen = korzen.getChild(2);
				Rec(korzen,x,y);
				korzen = korzen.getParent();
				korzen = korzen.getChild(3);
				Rec(korzen,x,y);
				x = x/2;
			}*/
		
	}
	
	/*public static void Rec(Node parent, int x, int y) {
		
		ArrayList<Point> list = new ArrayList<Point>();
		
		Point p1 = new Point(0,0);
		Point p2 = new Point(x/2,0);
		Point p3 = new Point(0,y/2);
		Point p4 = new Point(x/2,y/2);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		Node ch1 = parent.addChild(list);
		
		p1 = new Point(x/2,0);
		p2 = new Point(x,0);
		p3 = new Point(x/2,y/2);
		p4 = new Point(x,y/2);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		Node ch2 = parent.addChild(list);
		
		p1 = new Point(0,y/2);
		p2 = new Point(x/2,y/2);
		p3 = new Point(0,y);
		p4 = new Point(x/2,y);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		Node ch3 = parent.addChild(list);
		
		p1 = new Point(x/2,y/2);
		p2 = new Point(x,y/2);
		p3 = new Point(x/2,y);
		p4 = new Point(x,y);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		Node ch4 = parent.addChild(list);
	}*/
}
