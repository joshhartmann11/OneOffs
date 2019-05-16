import java.lang.Math;
class SortingAlgorithms{

// Bogo Sort		O(infinity)	E(n!)
// Insertion Sort	O(n^2)		E(n^2)
// Quick Sort 		O(n^2)		E(nlog(n))
// Radix Sort 		O(n)		E(n)
// Heap Sort 		O(nlog(n))	E(nlog(n))
// Bubble Sort 		O(n^2)		E(n^2)
// Merge Sort 		O(nlog(n))	E(nlog(n))
// Selection Sort 	O(n^2)		E(n^2)


// Not going to implement this because it randomly shuffles the array till the array is sorted.  Expected time is n!,
// therefor on a large input it would probably never end
public static int[] BogoSort(int Z[]){
	return Z;
}


// For every element in the list swap it with the previous one till it's in the right spot
public static int[] InsertionSort(int Z[]){
	int[] A = new int[Z.length];
	for(int i = 0; i < Z.length; i++) A[i] = Z[i];
	int j = 0;
	int temp;
	
	for(int i = 1; i < A.length; i++){
		j = i;
		while(j > 0 && A[j-1] > A[j]){
			temp = A[j-1];
			A[j-1] = A[j];
			A[j] = temp;
			j--;
		}
	}
	return A;
} // InsertionSort


// Pick a random key, split the array into higher and lower values than the key. combine the sorted lower then key
// then the higher array
public static int[] QuickSort(int[] Z){
	int[] A = new int[Z.length];
	for(int i = 0; i < Z.length; i++) A[i] = Z[i];
	
	if(A.length == 1 || A.length == 0 || A[1] == -1 || A[0] == -1) 
		return A;

	boolean same = true;
	for(int i = 1; i < A.length && A[i] != -1; i++){
		if(A[i-1] != A[i]){
			same = false;
			break;
		}
	}
	if(same)
		return A;
	
	int max = -1;
	for(int i = 0; i < A.length; i++){
		if(A[i] > max) max = A[i]; 
		if(A[i] == -1) break;
	}
	int pivot = -1;
	for(int i = 0; i < A.length; i++){
		if(A[i] == -1) break;
		if(A[i] != max){
			pivot = A[i];
			break;
		}
	}
	
	int[] B = new int[A.length];
	int[] C = new int[A.length];
	int b = 0;
	int c = 0;
	
	for(int i = 0; i < A.length && A[i] != -1; i++){
		if(A[i] <= pivot){
			B[b] = A[i];
			b++; 
		}else{
			C[c] = A[i];
			c++;
		}
	}
	
	C[c] = -1;
	B[b] = -1;
	
	B = QuickSort(B);
	C = QuickSort(C);
	int a = 0;
	for(b = 0; B[b] != -1; b++){
		A[a] = B[b];
		a++;
	} 
	for(c = 0; C[c] != -1; c++){
		A[a] = C[c];
		a++;
	} 
	return A;
} // QuickSort


// Find the highest value and calculate the roof of the squareroot of it and call it degree.  Sort all elements, mod
// 1, by putting them into bins from 0 to degree-1. Sort the previous result, mod degree, by putting them again into
// bins and return the result which is the original sorted array
public static int[] RadixSort(int[] Z){
	int[] A = new int[Z.length];
	for(int i = 0; i < Z.length; i++) A[i] = Z[i];
	int maxVal = -1;
	for(int i = 0; i < A.length; i++){
		if(A[i] > maxVal){
			maxVal = A[i];
		}
	}
	int degree = (int)Math.sqrt(maxVal) + 1;
	
	int[] output = new int[A.length];
	int[] count = new int[degree];

	for(int i = 0; i < A.length; i++){
		count[A[i] % degree]++;
	}
	
	for(int i = 1; i < count.length; i++){
		count[i] += count[i-1];
	}
	
	for(int i = A.length-1; i >= 0; i--){
		output[count[A[i] % degree] - 1] = A[i];
		count[A[i] % degree]--;
	}
	
	for(int i = 0; i < count.length; i++){
		count[i] = 0;
	}
	
	for(int i = 0; i < output.length; i++){
		count[(output[i]/degree) % degree]++;
	}
	
	for(int i = 1; i < count.length; i++){
		count[i] += count[i-1];
	}
	
	for(int i = output.length-1; i >= 0; i--){
		A[count[(output[i] / degree) % degree] - 1] = output[i];
		count[(output[i]/degree) % degree]--;
	}

	return A;
} // RadixSort


// Insert every item into a heap, represented by an array. Take out every item and place into an array
public static int[] HeapSort(int[] Z){
		int[] A = new int[Z.length];
		for(int i = 0; i < Z.length; i++) A[i] = Z[i];
		int[] heap = new int[A.length + 1];
		heap[0] = -1;
		int last = 1; // the last element int the heap
		int pos; // the position in question
		int temp;
		
		// Right Child = pos * 2 + 1
		// Left Child = pos * 2
		// Parent = pos / 2 (Integer Division)
		// Root = 1;
		
		// Puts all values into the heap
		for (int i = 0; i < A.length; i++) {
			heap[last] = A[i];
			pos = last;
			while (heap[pos] < heap[pos/2]) {
				temp = heap[pos];
				heap[pos] = heap[pos/2];
				heap[pos/2] = temp;
				pos = pos/2;
			}
			last++;
		}
		last--;
		
		// Takes the minimum value out, stores it in A, 
		// replaces the root and Bubbles down to retain heap
		for (int i = 0; i < A.length; i++) {
			A[i] = heap[1];
			heap[1] = heap[last];
			pos = 1;
			last--;
			while (((pos*2+1) <= last) || ((pos*2) <= last)) { // has children
				if (!((pos*2+1) <= last)) { // pos only has left child
					if (heap[pos] > heap[pos*2]) {
						temp = heap[pos];
						heap[pos] = heap[pos*2];
						heap[pos*2] = temp;
						pos = pos * 2;
					} else {
						break;
					} 
					
				} else { // pos has both left and right children
					if (heap[pos*2+1] > heap[pos*2]) { // if the left child is smaller
						if (heap[pos*2] < heap[pos]) {
							temp = heap[pos];
							heap[pos] = heap[pos*2];
							heap[pos*2] = temp;
							pos = pos * 2;
						} else {
							break;
						}
						
					} else { // if the right child is smaller
						if (heap[pos*2+1] < heap[pos]) {
							temp = heap[pos];
							heap[pos] = heap[pos*2+1];
							heap[pos*2+1] = temp;
							pos = (pos * 2) + 1;
						} else {
							break;
						}
					}
				}
			}
		}
		return A;
} // HeapSort


// Iterate through the array if the previous number is larger than the current number, swap the two. Continue until
// there are no swaps
public static int[] BubbleSort(int[] Z){
	int[] A = new int[Z.length];
	for(int i = 0; i < Z.length; i++) A[i] = Z[i];

	int swaps = 0;
	int temp;
	for(int i = 0; i < A.length; i++){
		swaps = 0;
		for(int j = 1; j < A.length; j++){
			if(A[j-1] > A[j]){
				temp = A[j-1];
				A[j-1] = A[j];
				A[j] = temp;
				swaps++;
			}
		}
		if (swaps == 0) return A;
	}
	return A;
} // BubbleSort


// Split the array into two even part, combine the sorted left then right, return.
public static int[] MergeSort(int[] Z){
	int[] A = new int[Z.length];
	for(int i = 0; i < Z.length; i++) A[i] = Z[i];

	if(A.length == 1){
		return A;
	}
	
	int[] B = new int[A.length/2];
	int[] C;
	if((A.length % 2) == 1){
		C = new int[(A.length/2)+1];
		C[A.length/2] = A[A.length-1];
	}else{
		C = new int[A.length/2];
	}
	
	for(int i = 0; i < A.length/2; i++){
		B[i] = A[i];
		C[i] = A[i+A.length/2];
	}
	
	B = MergeSort(B);
	C = MergeSort(C);
	
	int a = 0;
	int b = 0;
	int c = 0;
	while(a < A.length){
		if(c > C.length-1){
			A[a] = B[b];
			b++;	
		}else if(b > B.length-1){
			A[a] = C[c];
			c++;
		}else if(B[b] <= C[c]){
			A[a] = B[b];
			b++;
		}else if(C[c] < B[b]){
			A[a] = C[c];
			c++;
		}
		a++;
	}
	return A;
} // MergeSort


// Iterate through the list and swap the next lowest element with the current element
public static int[] SelectionSort(int[] A){
	int lowest;
	int temp;
	int[] B = new int[A.length];
	for(int i = 0; i < A.length; i++) B[i] = A[i];
	
	for(int i = 0; i < B.length; i++){
		lowest = i;
		for(int j = i; j < B.length; j++){
			if(B[j] < B[lowest]){
				lowest = j;
			}
		}
		temp = B[i];
		B[i] = B[lowest];
		B[lowest] = temp;
	}
	return B;
} // SelectionSort


public static void printArray(int[] A, boolean input, boolean print){
	if(!print) return;
	if(input){
		System.out.print("Input:  ");
	}else{
		System.out.print("Output: ");
	} 
	if(A.length == 0) return;
	System.out.print("[" + A[0]);
	for(int i = 1; i < A.length; i++){
		System.out.print("," + A[i]);
	}
	System.out.println("]");
} // printArray

public static void main(String args[]){
	int[] A = {10,5,15,20,1,5,3,2,45,7,12,6,9,43,2,5,37,2,8,8,12,23,12,45,87,34,12};
	int[] B = {0};
	int[] C = {16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0};
	boolean print = true;
	
	if(print) System.out.println("Testing Selection Sort");
	printArray(A, true, print);
	printArray(SelectionSort(A), false, print);
	printArray(B, true, print);
	printArray(SelectionSort(B), false, print);
	printArray(C, true, print);
	printArray(SelectionSort(C), false, print);
	
	if(print) System.out.println("\nTesting Merge Sort");
	printArray(A, true, print);
	printArray(MergeSort(A), false, print);
	printArray(B, true, print);
	printArray(MergeSort(B), false, print);
	printArray(C, true, print);
	printArray(MergeSort(C), false, print);

	if(print) System.out.println("\nTesting Bubble Sort");
	printArray(A, true, print);
	printArray(BubbleSort(A), false, print);
	printArray(B, true, print);
	printArray(BubbleSort(B), false, print);
	printArray(C, true, print);
	printArray(BubbleSort(C), false, print);
	
	if(print) System.out.println("\nTesting Heap Sort");
	printArray(A, true, print);
	printArray(HeapSort(A), false, print);
	printArray(B, true, print);
	printArray(HeapSort(B), false, print);
	printArray(C, true, print);
	printArray(HeapSort(C), false, print);
	
	if(print) System.out.println("\nTesting Radix Sort");
	printArray(A, true, print);
	printArray(RadixSort(A), false, print);
	printArray(B, true, print);
	printArray(RadixSort(B), false, print);
	printArray(C, true, print);
	printArray(RadixSort(C), false, print);
	
	if(print) System.out.println("\nTesting Quick Sort");
	printArray(A, true, print);
	printArray(QuickSort(A), false, print);
	printArray(B, true, print);
	printArray(QuickSort(B), false, print);
	printArray(C, true, print);
	printArray(QuickSort(C), false, print);
	
	if(print) System.out.println("\nTesting Insertion Sort");
	printArray(A, true, print);
	printArray(InsertionSort(A), false, print);
	printArray(B, true, print);
	printArray(InsertionSort(B), false, print);
	printArray(C, true, print);
	printArray(InsertionSort(C), false, print);
} // Main

} // class