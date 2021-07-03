/*
 * Maze program solves the maze for given input maze and returns solved path and directions taken to solve it
 * @author Kiran Machiraju
 */
public class Maze{
  char[][] maze;
  int mazeHeight;
  int mazeWidth;
  private static int[] ENTRANCE = {0,1};
  private static int[] EXIT = {0,3};
  StringBuilder sbuilder = new StringBuilder();
  
  //enum initialized for directions
  enum directions{
    E,W,N,S;
  }
  
  /*
   * Constructor for Maze class
   * 
   * @param maze
   */
  public Maze(char[][] maze){
    this.maze = maze;
    this.mazeHeight = maze.length;
    this.mazeWidth = maze[0].length;
  }
  
  /*
   * method to convert maze array to string
   * 
   * @return String 
   */
  public String toString(){
    StringBuilder sb = new StringBuilder();
    for(int row =0 ; row < maze.length; ++row){
      for(int column =0; column<maze[row].length;++column){
        sb.append(maze[row][column]);
      }
      sb.append("\n");
    }
     return sb.toString();   
  }
  
  /*
   * method to iterate through the maze and constructs the path from starting point to end point
   * 
   * @return String path of maze
   */
  public String solver(){
    String path = "";
    if(maze[ENTRANCE[0]][ENTRANCE[1]] == '#' || maze[EXIT[0]][EXIT[1]] == '#'){
      solve(ENTRANCE[0], ENTRANCE[1], directions.S);
      path ="";
    }else{
      maze[EXIT[0]][EXIT[1]] = '!';
			if (solve(ENTRANCE[0], ENTRANCE[1], directions.S)) {
				path = sbuilder.reverse().toString();
			}else {
				maze[EXIT[0]][EXIT[1]] = ' ';
			}
    }
    
    return path;
  }
  
  /*
   * method to iterate through the maze and check whether maze can be solved in given direction
   * 
   * @param row, row number of maze
   * @param column, column number of maze
   * @param d, direction of path
   * @return boolean, this returns whether given direction has correct path or not
   */
  private boolean solve(int row, int cloumn, directions d) {
	  
		boolean res = false;
		//set the current position with given direction
		if (d != null) {
			maze[row][cloumn] = d.toString().charAt(0);
		}
		
    //checking the dead end and set currect position to X if true
		if ((cloumn > 0 && maze[row][cloumn - 1] != ' ') && (cloumn < mazeWidth && maze[row][cloumn + 1] != ' ')
				&& (row > 0 && (maze[row - 1][cloumn] != ' ' && maze[row - 1][cloumn] != '!'))
				&& (row < mazeHeight && maze[row + 1][cloumn] != ' ')) {
			maze[row][cloumn] = 'X';
		}

		//if current position id exit the return true
		if (cloumn == EXIT[1] && row == EXIT[0]) {
			res = true;
		}
		
		//Setting the previous position of Maze with the given direction
		if (d == directions.E ) {
			maze[row][cloumn - 1] = directions.E.toString().charAt(0);
		}
		else if (d == directions.N) {
			maze[row + 1][cloumn] = directions.N.toString().charAt(0);
		}
		else if (d == directions.S && row!=0) {
			maze[row - 1][cloumn] = directions.S.toString().charAt(0);	
		}
		else if (d == directions.W) {
			maze[row][cloumn + 1] = directions.W.toString().charAt(0);
		}

		//moving the cursor to right 
		if (cloumn < mazeWidth && maze[row][cloumn + 1] == ' ' && solve(row, cloumn + 1, directions.E)) {
			maze[row][cloumn] = directions.E.toString().charAt(0);		
			sbuilder.append(directions.E);
			res = true;		
		}
    //moving cursor to north
		else if (row > 0 && maze[row - 1][cloumn] == ' ' && solve(row - 1, cloumn, directions.N)) {
			maze[row][cloumn] = directions.N.toString().charAt(0);			
			sbuilder.append(directions.N);
			res = true;			
		}
		else if (row > 0 && maze[row - 1][cloumn] == '!' && solve(row - 1, cloumn, null)) {
			maze[row][cloumn] = directions.N.toString().charAt(0);			
			sbuilder.append(directions.N);
			return true;		
		}
    //moving cursor to south
		else if (row < mazeHeight && maze[row + 1][cloumn] == ' ' && solve(row + 1, cloumn, directions.S)) {
			maze[row][cloumn] = directions.S.toString().charAt(0);			
			sbuilder.append(directions.S);
			res = true;		
		}
		//moving cursor to west
		else if (cloumn > 0 && maze[row][cloumn - 1] == ' ' && solve(row, cloumn - 1, directions.W)) {
			maze[row][cloumn] = directions.W.toString().charAt(0);		
			sbuilder.append(directions.W);
			res = true;	
		}

		return res;
	}
  
}
