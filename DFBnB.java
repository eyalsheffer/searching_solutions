import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DFBnB extends Algorithm {
    int _maxSpace = 0;
    private static final int INF = Integer.MAX_VALUE;
    public DFBnB(Board board, String order) {
        super(board, order);
    }
    @Override
    public int getMaxSpace() {
        return _maxSpace;
    }
    @Override
    public Node solve(){
        Stack<Node> stack = new Stack<>();
        Map<Node, Node> hashMap = new HashMap<>();
        Set<Node> outSet = new HashSet<>();
        int[] start = _board.getStart();
        Node startNode = new Node(start[0], start[1], false, null, "", 0, huristic(start[0], start[1]));
        stack.push(startNode);
        hashMap.put(startNode, startNode);
        Node result = null;
        int treshold = INF;////
        while(!stack.isEmpty()){
            if (hashMap.size() > _maxSpace) {
                _maxSpace = hashMap.size();
            }
            Node n = stack.pop();
            if (outSet.contains(n)) {
                hashMap.remove(n);
                outSet.remove(n);
            }
            else{
                outSet.add(n);
                stack.push(n);
                List<Node> kids = createKids(n);
                Collections.sort(kids);
                
                List<Node> validChildren = new ArrayList<>();
                for (Node g : kids) {
                    if (g.getF() >= treshold) {
                        break; 
                    }
                    if (hashMap.containsKey(g) && outSet.contains(hashMap.get(g))) {
                        continue;
                    }
                    if (hashMap.containsKey(g) && !outSet.contains(hashMap.get(g))) {
                        Node gPrime = hashMap.get(g);
                        if (gPrime.getF() <= g.getF()) {
                            continue;
                        } else {
                            stack.remove(gPrime);
                            hashMap.remove(gPrime);
                        }
                    }
                    
                    if (_board.getCell(g.getRow(), g.getCol()) == 'G') {
                        treshold = g.getF();
                        result = g;
                        break; 
                    }
                    
                    validChildren.add(g);
                }
                for (int i = validChildren.size() - 1; i >= 0; i--) {
                    Node validG = validChildren.get(i);
                    stack.push(validG);
                    hashMap.put(validG, validG);
                }
            }
        }
        return result;
    }
    
}
