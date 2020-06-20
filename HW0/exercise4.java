public class exercise4 {
	// public static void windowPosSum(int[] a, int n) {
	// 	int[] sum=new int[a.length];
	// for(int i=0; i<a.length; i=i+1){
	// 	sum[i]=0;
	// 	if (a[i]<=0){
	// 		sum[i]=a[i];
	// 		continue;
	// 	}
	// 	for(int j=i; j<=i+n; j=j+1){
	// 		if (j==a.length){
	// 			break;
	// 		}  		
	// 		sum[i]=sum[i]+a[j];
	// 	};
	// };
	// System.out.println(java.util.Arrays.toString(sum));
	// }

	/* a better and cleaner solution will be ... */
	public static void windowPosSum(int[] a, int n){
		int len = a.length;
		// we should operate directly on the inputs 
		// as we print the inputs after calling the function
		for (int i=0; i < len; i++){
			if (a[i] > 0){
				// assume n>0
				for (int j=i+1; j<=i+n; j++){
					if (j == len){
						break;
					}

					a[i] += a[j];

				}

			}
		}

	}

	public static void main(String[] args) {
		int[] a = {1, 2, -3, 4, 5, 4};
		int n = 3;
		windowPosSum(a, n);
		// Should print 4, 8, -3, 13, 9, 4
		System.out.println(java.util.Arrays.toString(a));
	}
}
  