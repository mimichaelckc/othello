package reversi;
import java.util.*;
public class reversi {
	private static final int size = 6;						//size of board
	public static int[][] board= new int[size][size];
	public static int dx[] = { 0, 0, 1, 1, 1, -1, -1, -1 },	//x-axis 8 direction increment
					  dy[] ={ 1, -1, 0, 1, -1, 0, 1, -1 };	//y-axis 8 direction increment
	public static  Scanner kb = new Scanner(System.in);
	public static int wCount=0,bCount=0; 					//Black & White chess counter
	
	public static void main(String args[]) 
	{
		board[board.length/2-1][board.length/2-1] = 1;			//default 4 chess on the board		
        board[board.length/2-1][(board.length/2)] = 2;			// use length to prevent size change	
        board[board.length/2][board.length/2-1] = 2;
        board[(board.length/2)][(board.length/2)] = 1;
		
        pBoard(board);
    	play();
    }
	
    private static int player(int round) {        			//player switching.
        if (round % 2 == 1)
            return 1;										
        else
            return 2;									
    }
	public static void pBoard(int[][] a) {       			//printing board
		System.out.println();
		System.out.println("    0 1 2 3 4 5 ");
		System.out.println("   ------------");
		for(int i=0;i<6;i++)
		{
			System.out.print(i+" | ");
			for(int j=0;j<6;j++)
			{
				System.out.print(a[i][j]+" ");				
			}
			System.out.println();
		}
	}

	public static boolean setCont(int x,int y,int round)    // different restriction when setting chess
	{
		if(bounding(x, y)) {
			System.out.printf("Error - input numbers should be 0 to %d!\n",board.length-1);       //use array.length in case user change the board size
			return false;
		}
		else if(board[x][y]!=0) {
			System.out.println("Error - input cell is not empty.");
			return false;		
		}
		else if(!checkDirct(x, y, round)) {
			System.out.println("Invalid Move");
			return false;
		}
		else return true;
		
	}
	
	public static boolean bounding(int x,int y)     	//a boolean to prevent out of bound
	{
		if(x>board.length-1 || x<0 ||y>board.length-1 || y<0) return true;
		else return false;
	}
	
	public static boolean nearbyChess(int x,int y,int round)			//check nearby chess if it is chess of current player or empty return true
	{
		if(board[x][y] == player(round)|| (board[x][y] == 0)) return true;
		else return false;
	}
	
	public static void setRev(int x, int y,int round)							// set and reverse the chess legally
	 {
        int x1, y1;
        for (int count=0,i = 0; i <dx.length; i++)
        {   count = 0;
            x1 = x + dx[i];
            y1 = y + dy[i];
            count++;
            if (bounding(x1, y1)||nearbyChess(x1, y1, round)) continue;
            while (!nearbyChess(x1, y1, round) )
            {
                x1 += dx[i];
                y1 += dy[i];
                count++;											
                if (bounding(x1, y1)||board[x1][y1] == 0) break;				// both true need to change direction
                if (board[x1][y1] == player(round))
                {
                    for(int j = 0; j <= count; j++)
                    {
                        board[ x ][ y] = player(round);
                        board[ x + j*dx[i] ][ y + j*dy[i] ] = player(round);
                    }
                    break;
	                }
	            }
	        }
    }

	 public static boolean checkDirct(int x, int y,int round)			//check 8 direction of x,y
	    {
	        int x1, y1;
	        for (int i = 0; i < dx.length; i++)
	        {
	            x1 = x + dx[i];
	            y1 = y + dy[i];
	            if (bounding(x1, y1)||nearbyChess(x1, y1, round)) continue;			// both true need to change direction
	            while (!nearbyChess(x1, y1, round))
	            {
	                x1 += dx[i];
	                y1 += dy[i];
	                if (bounding(x1, y1)||board[x1][y1] == 0) break;
	                if (board[x1][y1] == player(round))return true;
	                
	            }
	        }
	        return false;
	    }
	 
	public static boolean chessCounter()				//counting the black chess and white chess every cycle
	{
		bCount=0;wCount=0;
		 for(int i = 0; i< board.length; i++)
         {
             for(int j = 0; j< board[i].length; j++)
             {
                 if(board[i][j]==1)
                 {
                     bCount++;
                 }else if(board[i][j]==2)
                 {
                     wCount++;
                 }               
             }
         }
         if(wCount+bCount==board.length*board.length) {
             endStat();
             return true;
         }
      return false;
	}
	
	public static void endStat() 						// The end game statistic of 2 players and show winner
	{
		System.out.println("\nGame Finishes.");
        System.out.println("'1' - " + bCount);
        System.out.println("'2' - " + wCount);
        if (bCount > wCount) {
            System.out.println("Black wins.");
        } else if (bCount < wCount) {
            System.out.println("White wins.");
        } else if (bCount == wCount) {
            System.out.println("Draw!");
        }
	}
	
	public static void play() {
		int x,y,round=1;
		while(true)
		{								
				System.out.printf("\nPlease enter the position of '%d': ",player(round));
				x = kb.nextInt();
				y = kb.nextInt();
				if (!setCont(x,y,round))  continue;
				
				setRev(x, y, round);
				pBoard(board);
				
				if(chessCounter()) break;
				round++;
		}
	}
}
