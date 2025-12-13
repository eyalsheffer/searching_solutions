import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private boolean isTunnel(char c){
        return Character.isDigit(c);//there is a digit if and only if there is a tunnel
    }

    
}
