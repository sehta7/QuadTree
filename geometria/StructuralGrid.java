import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StructuralGrid extends JFrame{
	
	StructuralGrid(){
		setTitle("Siatka strukturalna");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BePanel panel = new BePanel();
		add(panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		StructuralGrid sg = new StructuralGrid();
	}

}

class BePanel extends JPanel{
	
	Graphics2D g2d;
	int lengthX = 300;
	int lengthY = 300;
	int deltaX = 30;
	int deltaY = 60;
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
			g2d.drawLine(myElement.get(j).el1.x, myElement.get(j).el1.y, myElement.get(j).el4.x, myElement.get(j).el4.y);
		}
	}
	
	
	public ArrayList<Point> Grid(int x, int y, int sizeX, int sizeY){
		ArrayList<Point> list = new ArrayList<Point>();
		int firstX = 0;
		int firstY = 0;
		
			for(int j = 0; j < sizeX/x; j++) {
				for(int k = 0; k < sizeY/y; k++) {
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
			PrintWriter write = new PrintWriter("C:\\Users\\Net\\Desktop\\olka\\geom\\triangles.txt");
			PrintWriter wr = new PrintWriter("C:\\Users\\Net\\Desktop\\olka\\geom\\vertices.txt");
			
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
}

class MyElement{
	
	public Point el1;
	public Point el2;
	public Point el3;
	public Point el4;
	public int index = 0;
	
	public MyElement(Point element1, Point element2, Point element3, Point element4) {
		el1 = element1;
		el2 = element2;
		el3 = element3;
		el4 = element4;
	}
	
	public MyElement(Point element1, Point element2, Point element3) {
		el1 = element1;
		el2 = element2;
		el3 = element3;
	}
	
	public void changeIndex() {
		index = 1;
	}
	
	public void addElement(Point element) {
		el1 = element;
	}
}

