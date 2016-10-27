package SudokuSolver;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Solver mySudoku = new Solver();
		// Main function making sudoku object class 
		System.out.println(mySudoku.array);
		for(int i=1;i<=9;i++){
			for(int j=0;j<=9;j++){
				System.out.print(mySudoku.array[i][j]);
			}
			System.out.println();
		}
	}

}
