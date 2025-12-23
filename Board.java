import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private char[][] _gameBoard;
    private int _rows;
    private int _cols;
    private int _startX, _startY, _goalX, _goalY;
    private HashMap<Character, List<int[]>> _tunnels;

    public Board(int rows, int cols, List<String> data){
        this._rows = rows;
        this._cols = cols;
        this._gameBoard = new char[rows][cols];
        this._tunnels = new HashMap<>();
        createBoard(data);

    }

    private void createBoard(List<String> lines){
        for (int i = 0; i < _rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < _cols; j++) {
                char c = line.charAt(j);
                _gameBoard[i][j] = c;
                if(c=='S'){
                    this._startX = i;
                    this._startY = j;
                }else if(c=='G'){
                    this._goalX = i;
                    this. _goalY =j;
                } else if(isTunnel(c)){
                    _tunnels.putIfAbsent(c,new ArrayList<>());
                    _tunnels.get(c).add(new int[]{i,j});
                } 
                
            }
        }
    }
    public char getCell(int x, int y){
        if(x<0 || x>= _rows || y<0 || y>=_cols){
            return '#';
        }
        return _gameBoard[x][y];
    }
    private boolean isTunnel(char c){
        return Character.isDigit(c);//there is a digit if and only if there is a tunnel
    }

    public int[] getTunnelMatch(char tunelNumber, int x, int y){
        List<int[]> tunnelSides = _tunnels.get(tunelNumber);
        if(tunnelSides==null || tunnelSides.size() < 2)//second check for preventing OOB later
            return null;
        for(int[] cell:tunnelSides){
            if(cell[0]!= x|| cell[1]!= y){
                return cell;
            }
        }
        return null;
    }


    public int[] getStart(){
        return new int[]{_startX,_startY};
    }

    public int[] getGoal(){
        return new int[]{_goalX,_goalY};
    }

    public int getrows(){
        return _rows;
    }
    public int getcols(){
        return _cols;
    }
    public Map<Character, List<int[]>> getunnels(){
        return _tunnels;
    }
    
}
