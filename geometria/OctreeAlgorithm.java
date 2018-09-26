import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.TreeNode;
import javax.xml.soap.Node;

public class OctreeAlgorithm extends JFrame{
	
	OctreeAlgorithm(){
		setTitle("Siatka strukturalna");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BePane panel = new BePane();
		add(panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		OctreeAlgorithm sg = new OctreeAlgorithm();
	}

}

class BePane extends JPanel{
	
	Graphics2D g2d;
	int lengthX = 300;
	int lengthY = 300;
	int deltaX = 20;
	int deltaY = 20;
	ArrayList<Point> myPoint = new ArrayList<Point>();
	ArrayList<MyElement> myElement = new ArrayList<MyElement>();
	ArrayList<MyElement> myTriangle = new ArrayList<MyElement>();
	
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		g2d = (Graphics2D) gr;
		
		myPoint = Grid(deltaX, deltaY, lengthX, lengthY);
		myElement = Matrix(myPoint, deltaX, deltaY);
		myTriangle = Triangles(myPoint, deltaX, deltaY);
		Save(myTriangle, myPoint);
		
		for(int i = 0; i < myTriangle.size(); i++) {
			System.out.println(myTriangle.get(i).el1 + ", " + myTriangle.get(i).el2 + ", " + myTriangle.get(i).el3);
		}
		
		for(int i = 0; i < myPoint.size(); i++) {
			g2d.drawOval(myPoint.get(i).x, myPoint.get(i).y, 1, 1);
			g2d.setColor(Color.BLACK);
			g2d.fillOval(myPoint.get(i).x, myPoint.get(i).y, 1, 1);
		}
		
		for(int j = 0; j < myPoint.size(); j++) {
			g2d.drawLine(myElement.get(j).el1.x, myElement.get(j).el1.y, myElement.get(j).el2.x, myElement.get(j).el2.y);
			g2d.drawLine(myElement.get(j).el2.x, myElement.get(j).el2.y, myElement.get(j).el4.x, myElement.get(j).el4.y);
			g2d.drawLine(myElement.get(j).el1.x, myElement.get(j).el1.y, myElement.get(j).el3.x, myElement.get(j).el3.y);
			g2d.drawLine(myElement.get(j).el3.x, myElement.get(j).el3.y, myElement.get(j).el4.x, myElement.get(j).el4.y);
			//g2d.drawLine(myElement.get(j).el1.x, myElement.get(j).el1.y, myElement.get(j).el4.x, myElement.get(j).el4.y);
		}
		
		
		
		for(MyElement el : myElement) {
			if(el.el1.x > 20 && el.el1.x < 80 && el.el1.y > 40 && el.el1.y < 100) {
				el.changeIndex();
			}
		}
		
		for(MyElement el : myElement) {
			if(el.el1.x > 100 && el.el1.x < 130 && el.el1.y > 250 && el.el1.y < 280) {
				el.changeIndex();
			}
		}
		
		for(int i = 0; i < myElement.size(); i++) {
			if(myElement.get(i).index == 1) {
				g2d.setColor(Color.RED);
				g2d.fillRect(myElement.get(i).el1.x, myElement.get(i).el1.y, 5, 5);
			}
		}
		
		//OctreeGrid(10, myElement, lengthX, lengthY);
	}
	
	
	public ArrayList<Point> Grid(int x, int y, int sizeX, int sizeY){
		ArrayList<Point> list = new ArrayList<Point>();
		int firstX = 0;
		int firstY = 0;
		
			for(int j = 0; j < sizeX; j = j + x) {
				for(int k = 0; k < sizeY; k = k + y) {
					firstX = firstX + x;
					Point point = new Point(firstX, firstY);
					list.add(point);
				}
				firstX = 0;
				firstY = firstY + y;
			}
		
		return list;
	}
	
	public ArrayList<MyElement> Matrix(ArrayList<Point> points, int x, int y){
		ArrayList<MyElement> list = new ArrayList<MyElement>();
		Point p1 = new Point();
		Point p2 = new Point();
		Point p3 = new Point();
		Point p4 = new Point();
		
		for(int i = 0; i < points.size(); i++) {
				p1 = new Point(points.get(i).x, points.get(i).y);
				p2 = new Point(points.get(i).x + x, points.get(i).y);
				p3 = new Point(points.get(i).x, points.get(i).y + y);
				p4 = new Point(points.get(i).x + x, points.get(i).y + y);
			
			
			MyElement element = new MyElement(p1, p2, p3, p4);
			list.add(element);
			//System.out.println(list.get(i).el1 + ", " + list.get(i).el2 + ", " + list.get(i).el3 + ", " + list.get(i).el4);
		}
		
		return list;
	}
	
	public ArrayList<MyElement> Triangles(ArrayList<Point> points, int x, int y){
		ArrayList<MyElement> list = new ArrayList<MyElement>();
		Point p1 = new Point();
		Point p2 = new Point();
		Point p3 = new Point();
		
		for(int i = 0; i < points.size(); i++) {
			p1 = new Point(points.get(i).x, points.get(i).y);
			p2 = new Point(points.get(i).x + x, points.get(i).y);
			p3 = new Point(points.get(i).x, points.get(i).y + y);
			
			if(TriangleHelicity(p1, p2, p3) == 1) {
				MyElement element = new MyElement(p1, p2, p3);
				list.add(element);
			}
			else if(TriangleHelicity(p1, p3, p2) == 1) {
				MyElement element = new MyElement(p1, p3, p2);
				list.add(element);
			}
		}
		
		for(int i = 0; i < points.size(); i++) {
			p1 = new Point(points.get(i).x + x, points.get(i).y + y);
			p2 = new Point(points.get(i).x + x, points.get(i).y);
			p3 = new Point(points.get(i).x, points.get(i).y + y);
			
			if(TriangleHelicity(p1, p2, p3) == 1) {
				MyElement element = new MyElement(p1, p2, p3);
				list.add(element);
			}
			else if(TriangleHelicity(p1, p3, p2) == 1) {
				MyElement element = new MyElement(p1, p3, p2);
				list.add(element);
			}
		}
		
		return list;
	}
	
	public void Save(ArrayList<MyElement> list, ArrayList<Point> listPoints) {
		try {
			PrintWriter write = new PrintWriter("D:\\Moje\\triangles.txt");
			PrintWriter wr = new PrintWriter("D:\\Moje\\vertices.txt");
			
			for(int i = 0; i < list.size(); i ++) {
				write.println("[" + i + "] = " + list.get(i).el1 + ", " + list.get(i).el2 + ", " + list.get(i).el3 + " - " + listPoints.indexOf(list.get(i).el1) + " - " + listPoints.indexOf(list.get(i).el2) + " - " + listPoints.indexOf(list.get(i).el3));
			}
			
			for(int i = 0; i < listPoints.size(); i ++) {
				wr.println("[" + i + "] = " + listPoints.get(i));
			}
			
			wr.close();
			write.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Tree OctreeGrid(int minEl, ArrayList<MyElement> list, int maxWidth, int maxHight) {
		ArrayList<MyElement> first = new ArrayList<MyElement>();
		ArrayList<MyElement> second = new ArrayList<MyElement>();
		ArrayList<MyElement> third = new ArrayList<MyElement>();
		ArrayList<MyElement> fourth = new ArrayList<MyElement>();
		int max1, max2, max3, max4; 
		Tree tree = new Tree();
		//tree.addParent(list);
		int el = list.size();
		
		if(checkCells(list) == 1) {
			for(int i = 0; i < list.size(); i++) {
				//System.out.println("lista: " + list.get(i).el1.x);
				if(list.get(i).el1.x < maxWidth/2 && list.get(i).el1.y < maxHight/2) {
					first.get(i).el1 = list.get(i).el1;
					//first.get(i).add(list.get(i).el1);
				}
				else if(list.get(i).el1.x > maxWidth/2 && list.get(i).el1.y < maxHight/2) {
					second.get(i).el1 = list.get(i).el1;
				}
				else if(list.get(i).el1.x > maxWidth/2 && list.get(i).el1.y > maxHight/2) {
					third.get(i).el1 = list.get(i).el1;
				}
				else if(list.get(i).el1.x < maxWidth/2 && list.get(i).el1.y > maxHight/2) {
					fourth.get(i).el1 = list.get(i).el1;
				}
			}
			
			
		while(minEl != el) {
			el = el/2;
			OctreeGrid(el, first, maxX(first), maxY(first));
			OctreeGrid(el, second, maxX(second), maxY(second));
			OctreeGrid(el, third, maxX(third), maxY(third));
			OctreeGrid(el, fourth, maxX(fourth), maxY(fourth));
		}
		}
		
		
		return tree;
	}
	
	public int TriangleHelicity(Point p1, Point p2, Point p3) {
		if(Determin(p1, p2, p3) > 0) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public double TriangleArea(int d) {
		return 0.5*Math.abs(d);
	}
	
	public int Determin(Point p1, Point p2, Point p3) {
		return (p1.x-p3.x)*(p2.y-p3.y) - (p2.x-p3.x)*(p1.y-p3.y);
	}
	
	public int maxX(ArrayList<MyElement> list) {
		int max = 0;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).el1.x > max) {
				max = list.get(i).el1.x;
			}
		}
		
		return max;
	}
	
	public int maxY(ArrayList<MyElement> list) {
		int max = 0;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).el1.y > max) {
				max = list.get(i).el1.y;
			}
		}
		
		return max;
	}
	
	public int checkCells(ArrayList<MyElement> list) {
		int count = 0;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).index == 1) {
				count++;
			}
		}
		
		if(count == list.size()) {
			return 0;
		}
		else {
			return 1;
		}
	}
}

class Tree{
	ArrayList<MyElement> parent = new ArrayList<MyElement>();
	ArrayList<MyElement> child = new ArrayList<MyElement>();
	
	public void addChild(ArrayList<MyElement> list){
		child = list;
	}
	
	public void addParent(ArrayList<MyElement> list) {
		parent = list;
	}
	
}
