public class Rekurencja {
	
	public static void Rek(int zm) {
		int x = 0;
		
		for(int i = 0; i < 10; i++) {
			System.out.println("i: " + i + ", x: " + x + ", zm: " + zm);
			x++;
		}
		zm++;
		
		if(zm == 5) {
			Rek(zm);
			zm--;
		}
		else if(zm ==3) {
			Rek(x);
			zm++;
		}
	}

	public static void main(String[] args) {
		Rekurencja re = new Rekurencja();
		int a = 0;
		Rek(a);

	}

}
