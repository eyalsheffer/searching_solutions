import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class IDAStar extends Algorithm{

    private int _maxSpace = 0;
    private static final int INF = Integer.MAX_VALUE;
    public IDAStar(Board board, String order,String openMode){
        super(board,order,openMode);
    }
    @Override
    public int getMaxSpace(){
        return _maxSpace;
    }
    @Override
    public Node solve(){
        Stack<Node> stack = new Stack<>();
        Map<Node, Node> hashMap = new HashMap<>();
        Set<Node> outSet = new HashSet<>();
        int[] start = _board.getStart();
        Node startNode = new Node(start[0], start[1], false, null, "", 0, huristic(start[0], start[1]));
        int treshold = startNode.getH();
        while(treshold !=INF){
            int minF = INF;
            stack.clear();
            hashMap.clear();
            outSet.clear();
            stack.push(startNode);
            hashMap.put(startNode, startNode);
            while (!stack.isEmpty()) {
                if (_withOpen) {
                    System.out.println("Open List: " + stack);
                }
                if(hashMap.size() > _maxSpace){
                    _maxSpace = hashMap.size();
                } 
                Node n = stack.pop();
                if(outSet.contains(n)){
                    hashMap.remove(n);
                    outSet.remove(n);
                }   
                else{
                    outSet.add(n);
                    stack.push(n);
                    for(Node g:createKids(n)){
                        if(g.getF() > treshold){
                            minF = Math.min(minF, g.getF());
                            continue;
                        }
                        if(hashMap.containsKey(g)&&outSet.contains(hashMap.get(g))){//loop
                            continue;
                        }
                        if (hashMap.containsKey(g) && !outSet.contains(hashMap.get(g))){
                            Node gTag = hashMap.get(g);
                            if (gTag.getF() > g.getF()){
                                stack.remove(gTag); 
                                hashMap.remove(gTag);
                            }else {
                                continue;
                            }

                        }
                        if (_board.getCell(g.getRow(), g.getCol()) == 'G') {
                            return g;
                        }
                        stack.push(g);
                        hashMap.put(g, g);
                    }
                }
                
            }
            treshold = minF;
           
        }
         return null;
    }
    
}
