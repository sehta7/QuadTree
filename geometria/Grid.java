import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.html.parser.Element;

//algorithm to make grid more dense by QuadTree algorithm
public class Grid extends JFrame{
	
	Grid(){
		setTitle("QuadTree Algorithm");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panel panel = new Panel();
		add(panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		Grid gr = new Grid();
	}

}

class Panel extends JPanel{
	
	Graphics2D g2d;
	ArrayList<MyElement> toSave = new ArrayList<MyElement>();
	
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		g2d = (Graphics2D) gr;
		ArrayList<MyElement> element = new ArrayList<MyElement>();
		ArrayList<Point> points = new ArrayList<Point>();
		//size of smallest element and size of grid
		int size = 5;
		int X = 400;
		int Y = 400;
		
		//makes regular grid consist of squares
		points = Grid(size,size,X,Y);
		element = Matrix(points, size, size);
		
		//starts the tree
		Node root = new Node(null, element);
		ArrayList<Node> nodeList = new ArrayList<Node>();
		nodeList.add(root);
		
		//makes one random polygon - square
		Random rand = new Random();
		int a = rand.nextInt(200);
		int b = rand.nextInt(400);
		int c = rand.nextInt(200);
		int d = rand.nextInt(400);
		
		//coloring random polygon
		for(MyElement el : element) {
			if(el.el1.x > a && el.el1.x < b && el.el1.y > c && el.el1.y < d) {
				el.changeIndex();
			}
		}
		
		//coloring constant element
		for(MyElement el : element) {
			if(el.el1.x > 10 && el.el1.x < 50 && el.el1.y > 30 && el.el1.y < 70) {
				el.changeIndex();
			}
		}
		
		for(int i = 0; i < element.size(); i++) {
			if(element.get(i).index == 1) {
				g2d.setColor(Color.RED);
				g2d.fillRect(element.get(i).el1.x, element.get(i).el1.y, size, size);
			}
		}
		//makes the border
		g2d.setColor(Color.BLACK);
		g2d.drawLine(400, 0, 400, 400);
		g2d.drawLine(0, 400, 400, 400);
		g2d.drawLine(0, 0, 0, 400);
		g2d.drawLine(0, 0, 400, 0);
		
		//method with recursion
		Rec(nodeList, g2d);
		//saving elements in file
		SaveEl(toSave);
	}
	
	//method with recursion to check the state of children
	public void Rec (ArrayList<Node> list, Graphics2D g){
		//array of 4 children
		ArrayList<MyElement>[] div = new ArrayList[4];
		ArrayList<Node> newNode = new ArrayList<Node>();
		
		//drawing the grid for every child
		for(int i =0; i < list.size(); i ++) {
			DrawGrid(list.get(i), g);
		}
		
		for(int i =0; i < list.size(); i ++) {
			//divide list on 4
			div = Divide(list.get(i));
			for(int j = 0; j < 4; j++) {
				//checks the state of child
				if(Check(div[j]) == 1) {
					//adding new node to check
					Node child = new Node(list.get(i),div[j]);
					newNode.add(child);	
				}
			}
		}	

		//if child has one point, remove it
		for(int i = 0; i < newNode.size(); i++) {
			if(newNode.get(i).points.size() == 1) {
				newNode.remove(newNode.get(i));
			}
		}
		
		//as long node has size bigger than 0, divide it
		if(newNode.size() != 0) {
			Rec(newNode, g);				
		}
	}
	
	//method to draw dense grid
	public void DrawGrid(Node node, Graphics2D g) {
		g.setColor(Color.BLACK);
		
		//finding size of element
		int xMax = FindMaxX(node.points);
		int yMax = FindMaxY(node.points);
		int xMin = FindMinX(node.points);
		int yMin = FindMinY(node.points);
		int distX = xMax - xMin;
		int distY = yMax - yMin;
		
		//drawing 2 cross lines
		g.drawLine(xMin+distX/2,yMin,xMin+distX/2,yMax);
		g.drawLine(xMin,yMin+distY/2,xMax,yMin+distY/2);
		
		//drawing border
		g.drawLine(xMin,yMin,(xMin+distX/2),(yMin+distY/2));
		g.drawLine(xMin+distX/2,yMin,xMax,(yMin+distY/2));
		g.drawLine(xMin,yMin+distY/2,(xMin+distX/2),yMax);
		g.drawLine(xMin+distX/2,yMin+distY/2,xMax,yMax);
		
	}
	
	//method to find max value of coordinate x
	public int FindMaxX(ArrayList<MyElement> list) {
		int max = 0;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).el4.x > max) {
				max = list.get(i).el4.x;
			}
		}
		
		return max;
	}
	
	//method to find max value of coordinate y
	public int FindMaxY(ArrayList<MyElement> list) {
		int max = 0;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).el4.y > max) {
				max = list.get(i).el4.y;
			}
		}
		
		return max;
	}
	
	//method to find min value of coordinate x
	public int FindMinX(ArrayList<MyElement> list) {
		int min = 500;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).el1.x < min) {
				min = list.get(i).el1.x;
			}
		}
		
		return min;
	}
	
	//method to find min value of coordinate y
	public int FindMinY(ArrayList<MyElement> list) {
		int min = 500;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).el1.y < min) {
				min = list.get(i).el1.y;
			}
		}
		
		return min;
	}
	
	//method to divide node element on 4 children
	public ArrayList<MyElement>[] Divide(Node groot){
		//array with children to return
		ArrayList<MyElement>[] tab = new ArrayList[4];
		
		//finding size of element
		int xMax = FindMaxX(groot.points);
		int yMax = FindMaxY(groot.points);
		int xMin = FindMinX(groot.points);
		int yMin = FindMinY(groot.points);
		int distX = xMax - xMin;
		int distY = yMax - yMin;
		
		//arraies of elements for every child
		ArrayList<MyElement> l1 = new ArrayList<MyElement>();
		ArrayList<MyElement> l2 = new ArrayList<MyElement>();
		ArrayList<MyElement> l3 = new ArrayList<MyElement>();
		ArrayList<MyElement> l4 = new ArrayList<MyElement>();
		
		//dividing list of points by condition of half distance
		if(groot.points.size() == 4) {
			for(MyElement el : groot.points) {
				if(el.el1.x < xMin+(distX/2) && el.el1.y < yMin+(distY/2)) {
					l1.add(el);
				}
				if(el.el2.x > xMin+(distX/2) && el.el2.y < yMin+(distY/2)) {
					l2.add(el);
				}
				if(el.el3.x < xMin+(distX/2) && el.el3.y > yMin+(distY/2)) {
					l3.add(el);
				}
				if(el.el4.x > xMin+(distX/2) && el.el4.y > yMin+(distY/2)) {
					l4.add(el);
				}
			}
		}
		else {
			for(MyElement el : groot.points) {
				if(el.el1.x <= xMin+(distX/2) && el.el1.y <= yMin+(distY/2)) {
					l1.add(el);
				}
				if(el.el2.x >= xMin+(distX/2) && el.el2.y <= yMin+(distY/2)) {
					l2.add(el);
				}
				if(el.el3.x <= xMin+(distX/2) && el.el3.y >= yMin+(distY/2)) {
					l3.add(el);
				}
				if(el.el4.x >= xMin+(distX/2) && el.el4.y >= yMin+(distY/2)) {
					l4.add(el);
				}
			}	
		}
	
		tab[0] = l1;
		tab[1] = l2;
		tab[2] = l3;
		tab[3] = l4;
		
		return tab;
	}
	
	public int Check(ArrayList<MyElement> list) {
		//count of colored elements
		int c = 0;
		
		//counting colored elements
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).index == 1){
				c++;
			}
		}
		
		//finished if child is full or empty
		if(c == list.size() || c == 0) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	//method to save elements in file
	public void SaveEl(ArrayList<MyElement> list) {
		try {
			//open file
			PrintWriter write = new PrintWriter("D:\\Moje\\triangles.txt");
			
			for(int i = 0; i < list.size(); i ++) {
				write.println("[" + i + "] = " + list.get(i).el1 + ", " + list.get(i).el2 + ", " + list.get(i).el3);// + " - " + listPoints.indexOf(list.get(i).el1) + " - " + listPoints.indexOf(list.get(i).el2) + " - " + listPoints.indexOf(list.get(i).el3));
			}
			
			//close file
			write.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//method to make a grid
	public ArrayList<Point> Grid(int x, int y, int sizeX, int sizeY){
		ArrayList<Point> list = new ArrayList<Point>();

		//makes points in the distance of x/y and in size sizeX/sizeY
		for(int i = 0; i < sizeX; i = i + x) {
			for(int j = 0; j < sizeY; j = j + y) {
				Point point = new Point(i, j);
				list.add(point);
			}
		}
		return list;
	}
	
	//method to make the array with elements
	public ArrayList<MyElement> Matrix(ArrayList<Point> points, int x, int y){
		ArrayList<MyElement> list = new ArrayList<MyElement>();
		Point p1 = new Point();
		Point p2 = new Point();
		Point p3 = new Point();
		Point p4 = new Point();
		
		int xMax = 0;
		int yMax = 0;
		
		for(int i = 0; i < points.size(); i++) {
			if(points.get(i).x > xMax) {
				xMax = points.get(i).x;
			}
			if(points.get(i).y > yMax) {
				yMax = points.get(i).y;
			}
		}
		
		//adding element from every point
		for(int i = 0; i < points.size(); i++) {
			if(points.get(i).x < xMax && points.get(i).y < yMax) {
				p1 = new Point(points.get(i).x, points.get(i).y);
				p2 = new Point(points.get(i).x + x, points.get(i).y);
				p3 = new Point(points.get(i).x, points.get(i).y + y);
				p4 = new Point(points.get(i).x + x, points.get(i).y + y);
				
				MyElement element = new MyElement(p1, p2, p3, p4);
				list.add(element);
			}			
			//System.out.println(list.get(i).el1 + ", " + list.get(i).el2 + ", " + list.get(i).el3 + ", " + list.get(i).el4);
		}
		
		return list;
	}
}
