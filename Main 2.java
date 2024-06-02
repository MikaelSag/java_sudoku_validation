import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

public class Main
{
   public static boolean LoadFile(String filename, int [][] data)
   {
        try 
        {
            FileInputStream sudokuFileStream = new FileInputStream(filename);
            Scanner filescnr = new Scanner(sudokuFileStream);
            for (int row = 0; row < 9; row++)      // loop through the 9x9 array setting nextInt to the next open index
            {
                for(int col = 0; col < 9; col++)
                {
                    data[row][col] = filescnr.nextInt();
                }
            }
            for (int row1 = 0; row1 < 9; row1++)
            {
                for (int col1 = 0; col1 < 9; col1++)
                {
                    if (data[row1][col1] == 0)     // if any index of the array is equal to zero it is invalid or the file was short
                    {
                        return false;
                    }
                }
            }
            return true;
        } 
        catch(Exception e) 
        {
            return false;     //if there's an error return false and the program will be terminated in main
        }
   }
   
   public static boolean TestRow(int [][] data, int row)
   {
       int incorrectRow = 0;
       int previousWrong = 0;
       for (int row1 = 0; row1 < 9; row1++)
       {
           for (int col = 0; col < 9; col++)
           {
               for (int col1 = 0; col1 < 9; col1++)
               {
                    if (col != col1)   // loop through 2d array 3 times so you can compare each value with every other in the same row to find duplicates
                    {
                        if (data[row1][col] == data[row1][col1])
                        {
                            incorrectRow = row1 + 1;
                            if (incorrectRow != previousWrong)    // keep prev wrong stored so program doesnt print invalid row for the same row twice
                                {
                                    System.out.println("Row " + incorrectRow + " is invalid.");
                                    previousWrong = incorrectRow;
                                }
                        }
                    }
               }
           }
       }
       if (incorrectRow == 0)
       {
           return true;    // incorrectRow was initialized to zero so if it is still zero there were no duplicates 
       }
       else
       {
           return false;
       }
   }
   
   public static boolean TestColumn(int [][] data, int col)
   {
       int incorrectCol = 0;
       int previousWrong = 0;
       for (int col1 = 0; col1 < 9; col1++)
       {
           for (int row = 0; row < 9; row++)    // exact same process ass the TestRow
           {
               for (int row1 = 0; row1 < 9; row1++)
               {
                    if (row != row1)
                    {
                        if (data[row][col1] == data[row1][col1])
                        {
                            incorrectCol = col1 + 1;
                            if (incorrectCol != previousWrong)
                                {
                                    System.out.println("Column " + incorrectCol + " is invalid.");
                                    previousWrong = incorrectCol;
                                }
                        }
                    }
               }
           }
       }
       if (incorrectCol == 0)
       {
           return true;
       }
       else
       {
           return false;
       }
   }
   
   public static boolean TestBox(int [][] data, int box)
   {
       int [] temp = new int[9];
       int counter = 0;
       int x = 0;
       int y = 0;
       boolean check = true;
       
       switch(box)      // switch case for each box to adjust nested for loop as needed
       {
            case 1:
               break;
            case 2:
                x = 3;
                y = 0;
                break;
            case 3:
                x = 6;
                y = 0;
                break;
            case 4:
                x = 0;
                y = 3;
                break;
            case 5: 
                x = 3;
                y = 3;
                break;
            case 6:
                x = 6;
                y = 3;
                break;
            case 7:
                x = 0;
                y = 6;
                break;
            case 8:
                x = 3;
                y = 6;
                break;
            case 9:
                x = 6;
                y = 6;
                break;
       }
       
       for (int row = 0 + y; row < 3 + y; row++)      // the +x and +y values were calculated and accounted for in switch statement to make for loops work for all 9 boxes
       {
           for (int col = 0 + x; col < 3 + x; col++)
           {
               temp[counter] = data[row][col];   // convert the 3x3 box into a 1 dimensional array so its easier to check for duplicates
               counter++;                       // counter initialized before for loops so its value stays as the loops restart making it go up to 9
           }
       }
       
       for (int i = 0; i < 9; i++)
       {
           for (int j = 0; j < 9; j++)
           {
               if (i != j)
               {
                   if (temp[i] == temp[j])
                   {
                       check = false;     // test for duplicates using same method as before but with single dimensional array
                   }
               }
           }
       }
       if (!check)
       {
           System.out.println("Box " + box + " is invalid.");
           return false;
       }
       else 
       {
           return true;
       }
   }
   
   public static void Display(int [][] data)
   {
       for (int row = 0; row < 9; row++)
       {
           for (int col = 0; col < 9; col++)
           {
               System.out.print(data[row][col]);
           }
           System.out.println();
       }
   }

   public static void main(String [] args)
   {
       int [][] data = new int [9][9];
       boolean checkfile;
       boolean checkRow;
       boolean checkCol;
       boolean checkBox;
       int rowTest = 0;
       int colTest = 0;
       int count = 0;
       String filename = null;
       
       Scanner filenamescnr = new Scanner(System.in);
       System.out.println("Enter the filename: ");
       filename = filenamescnr.nextLine();
       checkfile = LoadFile(filename, data);
       
       if (!checkfile)
       {
           System.out.println("Error reading file.");    // if file had issues opening or wasnt correct size print error messafe and end program
           System.exit(0);
       }
       
       Display(data);
       
       checkRow = TestRow(data, rowTest);
       checkCol = TestColumn(data, colTest);
       
       for (int loop = 1; loop < 10; loop++)
       {
           boolean countBool;
           countBool = TestBox(data, loop);  // call TestBox with box parameter 1-9 so every box is checked for duplicates
           if (countBool)
           {
               count++;    // keep count everytime TestBox returns true, so if count is lower than 9 one of the boxes must have failed meaning solution is invalid
           }
       }
       
       if ((checkRow) && (checkCol) && (count == 9))     // if TestRow and TestCol returned true and TestBox returned true 9 times the solution must be correct
       {
           System.out.println("The solution is valid.");
       }
       
   }
   
}