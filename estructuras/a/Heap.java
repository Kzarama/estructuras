package a;

import java.io.*;

class Heap<T> {
	
	private Node<T>[] heapArray;
	private int maxSize;
	private int currentSize;

   public Heap(int mx) {
      maxSize = mx;
      currentSize = 0;
      heapArray = new Node[maxSize];
      }

   public boolean isEmpty() {
	   return currentSize==0;
	   }

   public boolean insert(T key) {
      if(currentSize==maxSize)
         return false;
      Node<T> newNode = new Node<T>(key);
      heapArray[currentSize] = newNode;
      trickleUp(currentSize++);
      return true;
      }

   public void trickleUp(int index) {
      int parent = (index-1) / 2;
      Node<T> bottom = heapArray[index];

      while( index > 0 && heapArray[parent].getKey().equals( bottom.getKey() )) {
         heapArray[index] = heapArray[parent];
         index = parent;
         parent = (parent-1) / 2;
         }
      heapArray[index] = bottom;
      }

   public Node<T> remove() {
      Node<T> root = heapArray[0];
      heapArray[0] = heapArray[--currentSize];
      trickleDown(0);
      return root;
      }

   public void trickleDown(int index) {
      int largerChild;
      Node<T> top = heapArray[index];
      while(index < currentSize/2) {
         int leftChild = 2*index+1;
         int rightChild = leftChild+1;
         if(rightChild < currentSize && heapArray[leftChild].getKey() < heapArray[rightChild].getKey())
            largerChild = rightChild;
         else
            largerChild = leftChild;
         if( top.getKey() >= heapArray[largerChild].getKey() )
            break;
         heapArray[index] = heapArray[largerChild];
         index = largerChild;
         }
      heapArray[index] = top;
      }
   
   public boolean change(int index, int newValue) {
      if(index<0 || index>=currentSize)
         return false;
      int oldValue = (int) heapArray[index].getKey();
      heapArray[index].setKey(newValue);
      if(oldValue < newValue)
         trickleUp(index);
      else
         trickleDown(index);
      return true;
      }
   
//   public void displayHeap() {
//      System.out.print("heapArray: ");
//      for(int m=0; m<currentSize; m++)
//         if(heapArray[m] != null)
//            System.out.print( heapArray[m].getKey() + " ");
//         else
//            System.out.print( "-- ");
//      System.out.println();
//      int nBlanks = 32;
//      int itemsPerRow = 1;
//      int column = 0;
//      int j = 0;
//      String dots = "...............................";
//      System.out.println(dots+dots);      // dotted top line
//
//      while(currentSize > 0)              // for each heap item
//         {
//         if(column == 0)                  // first item in row?
//            for(int k=0; k<nBlanks; k++)  // preceding blanks
//               System.out.print(' ');
//                                          // display item
//         System.out.print(heapArray[j].getKey());
//
//         if(++j == currentSize)           // done?
//            break;
//
//         if(++column==itemsPerRow)        // end of row?
//            {
//            nBlanks /= 2;                 // half the blanks
//            itemsPerRow *= 2;             // twice the items
//            column = 0;                   // start over on
//            System.out.println();         //    new row
//            }
//         else                             // next item on row
//            for(int k=0; k<nBlanks*2-2; k++)
//               System.out.print(' ');     // interim blanks
//         }  // end for
//      System.out.println("\n"+dots+dots); // dotted bottom line
//      }  // end displayHeap()
// -------------------------------------------------------------
   }