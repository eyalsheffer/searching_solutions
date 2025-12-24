import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar extends Algorithm{
    private int _maxSpace = 0;
    public AStar(Board board, String order, String openMode) {
        super(board, order, openMode);
    }
    @Override
    public int getMaxSpace() {
        return _maxSpace;
    }
    @Override
    public Node solve(){
        int[] start = _board.getStart();
        Node startNode = new Node(start[0], start[1], false, null, "", 0, huristic(start[0], start[1]));
        Queue<Node> openList = new PriorityQueue<>();
        Map<Node, Node> nodeMap = new HashMap<>();//the closed list. ill change if a shorter path is found.
        openList.add(startNode);
        nodeMap.put(startNode, startNode);
        while (!openList.isEmpty()){
            if (_withOpen) {
                System.out.println("Open List: " + openList);
            }
            if (openList.size()>_maxSpace) {
                _maxSpace = nodeMap.size();
            }
            Node current = openList.poll();
            Node currentCheapest = nodeMap.get(current);
            if (currentCheapest!=null && currentCheapest.getG()<current.getG()) {//check if i already had cheaper. because i may already develop a cheaper solution
                continue; 
            }
            if (_board.getCell(current.getRow(), current.getCol()) == 'G') {
                return current;
            }
            for (Node kid : createKids(current)){
                if (nodeMap.containsKey(kid)) {
                    Node existing = nodeMap.get(kid);
                    
                    if (kid.getG() <existing.getG()) {//add kid only if better
                        nodeMap.put(kid, kid); 
                        openList.add(kid);          
                    }
                } else {
                    nodeMap.put(kid, kid);
                    openList.add(kid);
                }
            }
        }
        return null;
    }
}