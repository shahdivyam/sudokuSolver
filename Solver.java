package SudokuSolver;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Solver extends JFrame implements ActionListener {

	private JButton[][] board = new JButton[size + 1][size ];
	public int[][] array = new int[size + 1][size + 1];
	private static int size = 9;
	private static String solve = "Solve";
	private static String reset = "Reset";
	

	Solver() {
		super("Sudoku Solver ");
		super.setResizable(false);
		super.setSize(650, 600);

		GridLayout layout = new GridLayout(10, 9);
		super.setLayout(layout);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				JButton button = new JButton("");
				button.putClientProperty("column", j+1);
				button.putClientProperty("row", i+1);
				button.addActionListener(this);
				button.setFont(new Font("Times New Roman", 1, 50));
				button.setBackground(Color.cyan);
				board[i][j] = button;
				super.add(button);
			}
		}

		JButton button = new JButton(solve);
		button.addActionListener(this);
		board[size][0] = button;
		super.add(button);
		board[size][size-1] = new JButton(reset);
		board[size][size-1].addActionListener(this);
		super.add(board[size][size-1]);
		super.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton button = (JButton) e.getSource();
		String string = button.getText();
		if (string == solve) {
			int count = getCount();
			if(count<17){
				//solveSudoku();
				JOptionPane.showMessageDialog(null,"Hints are not Enough: "+ (17-count) +" more Hints required");
				button.setText(solve);
			}
			else if(isValidBoard()==false){
				JOptionPane.showMessageDialog(null,"Invalid Board Config ");
				return;
			}
			else{
				solveSudoku();
			}
			
		}
		if(string==reset){
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					board[i][j].setText("");
					array[i+1][j+1]=0;
				}
			}
		}
		else {
			String setText = updateValue(string);
			button.setText(setText);
			Boolean safe = true;
			if(setText.length()!=0){
				int value = setText.charAt(0)-'0';
				int row = (int) button.getClientProperty("row");
				int col = (int) button.getClientProperty("column");
				button.setText(setText);
				
				safe = isSafe(row, col);
				
				/*JOptionPane.showMessageDialog(null,row);
				JOptionPane.showMessageDialog(null,col);
				*/
			}
			
			if(safe==true){
				
				button.setForeground(Color.black);
			}
			else{
				
				button.setForeground(Color.red);
			}
			
			return;
		}

	}
	private void updateSudoku(){
		for(int i=1;i<=size;i++){
			for(int j=1;j<=size;j++){
				JButton button = board[i-1][j-1];
				if(button.getText()==""){
					
					button.setText(Integer.toString(array[i][j]));
					button.setForeground(Color.green);
				}
			}
		}
	}
	private void solveSudoku(){
		//array = getArray();
		System.out.println("PAY ATTENSTION "+func());
		updateSudoku();
		for(int i=1;i<=9;i++){
			for(int j=0;j<=9;j++){
				System.out.print(array[i][j]);
			}
			System.out.println();
		}
	}
	private Boolean func(){
		
		int x=0,y=0;
	    for(int i=1;i<10;i++){
	        for(int j=1;j<10;j++){
	            if(array[i][j]==0){
	            	
	                x=i;y=j;
	                System.out.println("X : "+x+"Y: "+y);
	                break;
	            }
	        }
	        if(x!=0&&y!=0){
	        	break;
	        }
	    }
	    System.out.println("X : "+x+"Y: "+y);
	    if(x==0&&y==0){return true;}
	    Boolean temp=false;
	    for(int i=1;i<=9;i++){
	    	array[x][y]=i;
	        if(isSafePlease(x,y)){
	        	System.out.println("X : "+x+"Y: "+y);
	                temp = func();
	        }
	        if(temp==true){
	            return true;
	        }
	        array[x][y]=0;
	    }
	    return temp;
	}


	private boolean isValidBoard(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				JButton button = board[i][j];
				 if(button.getForeground()==Color.blue){
					 return false;
				 }
			}
		}
		return true;
	}
	private int getCount(){
		int[][]matrix = getArray();
		int count=0;
		for(int i=1;i<=size;i++){
			for(int j=1;j<=size;j++){
				if(matrix[i][j]!=0){
					count++;
				}
			}
		}
		return count;
	}
	private String updateValue(String string) {
		String setText = "";
		if (string.length() == 0) {
			setText = "1";
		} else {
			char ch = string.charAt(0);
			if (ch == '9') {
				setText = "";
			} else {
				Integer t = ch - '0';
				t = t + 1;
				setText = Integer.toString(t);
			}
		}
		return setText;
	}
	
	
	// taken i+1,j+1,;;;
	//public int[][] getIntArray(){}
	public int[][] getArray(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				JButton button = board[i][j];
				String string  = button.getText();
				int val=0;
				if(string==""){
					val=0;
				}
				else{
					val = string.charAt(0)-'0';
				}
				array[i+1][j+1] = val;
			}
		}
		
		return array;
	}
	private Boolean isSafe(int row,int col){
	    int[][] matrix = getArray();
		for(int i=1;i<=9;i++){
	        if(i==col){continue;}
	        if(matrix[row][i]==matrix[row][col]){
	            return false;}
	        }
	    for(int i=1;i<=9;i++){
	        if(i==row){continue;}
	        if(matrix[i][col]==matrix[row][col]){
	            return false;}
	        }
	    int i = (row-1)/3;
	    int j = (col-1)/3;
	    if(i==0){
	        if(j==0){
	            for(int a=1;a<4;a++){
	                for(int b=1;b<4;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else if(j==1){
	            for(int a=1;a<4;a++){
	                for(int b=4;b<7;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else{
	            for(int a=1;a<4;a++){
	                for(int b=7;b<10;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	    }
	    else if(i==1){
	        if(j==0){
	            for(int a=4;a<7;a++){
	                for(int b=1;b<4;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else if(j==1){
	            for(int a=4;a<7;a++){
	                for(int b=4;b<7;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else{
	            for(int a=4;a<7;a++){
	                for(int b=7;b<10;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	    }
	    else if(i==2){
	        if(j==0){
	            for(int a=7;a<10;a++){
	                for(int b=1;b<4;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else if(j==1){
	            for(int a=7;a<10;a++){
	                for(int b=4;b<7;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else{
	            for(int a=7;a<10;a++){
	                for(int b=7;b<10;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	    }

	return true;
	}
	
	private Boolean isSafePlease(int row,int col){
	    int[][] matrix = array;
		for(int i=1;i<=9;i++){
	        if(i==col){continue;}
	        if(matrix[row][i]==matrix[row][col]){
	            return false;}
	        }
	    for(int i=1;i<=9;i++){
	        if(i==row){continue;}
	        if(matrix[i][col]==matrix[row][col]){
	            return false;}
	        }
	    int i = (row-1)/3;
	    int j = (col-1)/3;
	    if(i==0){
	        if(j==0){
	            for(int a=1;a<4;a++){
	                for(int b=1;b<4;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else if(j==1){
	            for(int a=1;a<4;a++){
	                for(int b=4;b<7;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else{
	            for(int a=1;a<4;a++){
	                for(int b=7;b<10;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	    }
	    else if(i==1){
	        if(j==0){
	            for(int a=4;a<7;a++){
	                for(int b=1;b<4;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else if(j==1){
	            for(int a=4;a<7;a++){
	                for(int b=4;b<7;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else{
	            for(int a=4;a<7;a++){
	                for(int b=7;b<10;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	    }
	    else if(i==2){
	        if(j==0){
	            for(int a=7;a<10;a++){
	                for(int b=1;b<4;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else if(j==1){
	            for(int a=7;a<10;a++){
	                for(int b=4;b<7;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	        else{
	            for(int a=7;a<10;a++){
	                for(int b=7;b<10;b++){
	                    if(a==row&&b==col){continue;}

	                    if(matrix[a][b]==matrix[row][col]){
	                    return false;}
	            }}
	        }
	    }

	return true;
	}
	

}
