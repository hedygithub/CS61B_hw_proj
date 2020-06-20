public class exercise1b {
	public static int N;
	public exercise1b(int givenN){
		N=givenN;
	}
	public static void drawTriangle (){
		int i=1;
		while (i<=N) {
			int j=2;
			while (j<=i){
				System.out.print('*');
				j=j+1;				
			}
			System.out.println('*');
			i=i+1;
		}
	}
}