import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;

public class BFS extends Algorithm {
    private int _maxSpace = 0;

    public BFS(Board board, String order){
        super(board, order);
    }
    @Override
    protected int huristic(int newRow, int newCol){
        return 0;
    }

    @Override
    public Node solve(){
        int[] start =_board.getStart();
        Node startNode = new Node(start[0], start[1], false,null,"",0,0);
        Queue<Node> openList = new LinkedList<>();
        Map<Node,Node> closedList = new HashMap<>();
        openList.add(startNode);
        closedList.put(startNode,startNode);
        while (!openList.isEmpty()) {
            if(openList.size() > _maxSpace){
                _maxSpace = openList.size();
            }
            Node currentNode = openList.poll();
            if(_board.getCell(currentNode.getRow(), currentNode.getCol())=='G'){
                return currentNode;
            }

            for(Node kid:createKids(currentNode)){
                if(!closedList.containsKey(kid)){
                    closedList.put(kid, kid);
                    openList.add(kid);
                }
            }
            
        }
        return null;

    }
    @Override
    public int getMaxSpace(){
        return _maxSpace;
    }
}
