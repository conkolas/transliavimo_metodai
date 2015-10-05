#include <stdio.h>
#include <stdlib.h>

int * bubbleSort(int *array, int size) {
	int sorted = 1;
	int temp;
	int i;
	
	while (sorted != 0) {
		sorted = 0;
		for (i=0; i < (size - 1); i++) {			
			if (array[i] > array[i+1]) {
				temp = array[i];
				array[i] = array[i+1];
				array[i+1] = temp;
				
				sorted++;
			}
		}	
	}
	
	return array;
}

int main() {
	
	int size;
	
	printf("Size of an array: ");
	scanf("%d", &size);
	
	int array[size];
	int i = 0;
	
	while (i < size) {
		printf("%d. Enter integer:", (i+1));
		scanf("%d", &array[i]);
		
		i++;
	}	
	
	int *sorted;
	sorted = bubbleSort(array, size);
	i = 0;
	
	while (i < size) {
		printf("%d, ", (sorted[i]));
		i++;
	}
	
	return 0;
}
