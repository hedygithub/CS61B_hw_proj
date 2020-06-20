public class exercise1a {
	public static void main(String[] args){
		int i=1;
		int total=5;
		while (i<=total) {
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