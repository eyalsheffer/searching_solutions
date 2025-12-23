import java.util.HashMap;
import java.util.Map;

public class DFID extends Algorithm{
    private static final Node _limitReached = new Node(-1,-1,false,null,"",0,0);//this w'll be the node ill return where ill reach the limit
    private int _maxSpace = 0;

    public DFID(Board board, String order){
        super(board, order);
    }
    
    @Override
    protected int huristic(int newRow, int newCol){
        return 0;
    }
    @Override
    public Node solve(){
        int[] start = _board.getStart();
        Node startNode = new Node(start[0], start[1], false,null,"",0,0);
        for (int i = 0; i <Integer.MAX_VALUE; i++) {//i is the level limition
            Map<Node, Node> HashMap = new HashMap<>();
            Node result = limitedDFS(startNode,i,HashMap);
            if(result!= _limitReached){
                return result;
            }
        
        }
        return null;
    }

    private Node limitedDFS(Node n, int limit, Map<Node,Node> hashMap) {
        if(_board.getCell(n.getRow(), n.getCol())=='G'){
            return n;
        }
        if(limit==0){
            return _limitReached;
        }
        hashMap.put(n, n);
        if(hashMap.size() > _maxSpace){
            _maxSpace = hashMap.size();
        }
        
        boolean limitionReached = false;
        for(Node g:createKids(n)){
            if(hashMap.containsKey(g)) continue;
            Node result = limitedDFS(g, limit-1, hashMap);
            if(result == _limitReached){
                limitionReached = true;
            }
            else if (result!= null) {
                return result;
            }
           
        }
        hashMap.remove(n);
        if ((limitionReached)) {
            return _limitReached;
            
        }
        return null;
    }
    @Override
    public int getMaxSpace(){
        return _maxSpace;
    }

}
