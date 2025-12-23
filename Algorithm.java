import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm {
    protected Board _board;
    protected String _order;
    protected int _sumOfNodes = 0;
    //for every dir the prograss matches including the name of the actions and in order of clock wise
    //for counter clock wise i'll just change the order
    protected final int[] _rowDir = {0,1,1,1,0,-1,-1,-1};
    protected final int[] _colDir = {1,1,0,-1,-1,-1,0,1};
    protected final String[] _action = {"R","RD","D","LD","L","LU","U","RU"};

    public Algorithm(Board board, String order){
        this._board = board;
        this._order = order;
        if (order.contains("first-new")) {
            Node.compareNewFirst = true;
        } else {
            Node.compareNewFirst = false;
        }
    }

    public int getSumOfNodes(){
        return _sumOfNodes;
    }

    protected List<Node> createKids(Node current){
        //gets the current node
        //returns a list of his valid kids

        List<Node> kids = new ArrayList<>();
        int currentRow = current.getRow();
        int currentCol = current.getCol();
        boolean hasGearUp = current.hasGearUp();
        //now w'll decide on what order to create the kids
        //always start from R than do as asked
        int[] directions;
        if(_order.contains("counter")){
            directions = new int[]{0,7,6,5,4,3,2,1};
        } else{
            directions = new int[]{0,1,2,3,4,5,6,7};
        }
        for(int i:directions){
            
            int newRow = currentRow+_rowDir[i];
            int newCol = currentCol+_colDir[i];
            char cell = _board.getCell(newRow, newCol);
            if(cell=='#') continue;
            int cost = getMoveCost(currentRow,currentCol, newRow,newCol,cell,hasGearUp);
            //smooth floor no gear
            if(cost==-1) continue;
            int kidsH = huristic(newRow,newCol);
            Node child = new Node(newRow, newCol, hasGearUp || (cell =='*'), current, _action[i],current.getG() + cost, kidsH);
            if(current.getParent()!= null && child.equals(current.getParent())){
                continue;
            }
            kids.add(child);
        }
        //check tunnels
        char cell = _board.getCell(currentRow, currentCol);
        if(Character.isDigit(cell)){
            int[] otherSide = _board.getTunnelMatch(cell, currentRow, currentCol);
            if(otherSide!= null){
                int kidsH = huristic(otherSide[0], otherSide[1]);
                Node child = new Node(otherSide[0], otherSide[1], hasGearUp, current, "Ent",current.getG() +2 ,kidsH );
                if(!(current.getParent()!= null && child.equals(current.getParent()))){
                    kids.add(child);
                }
                
            }
        }
        _sumOfNodes+= kids.size();
        return kids;
    }
    public abstract Node solve();
    
    public abstract int getMaxSpace();

    private int getMoveCost(int currentRow, int currentCol, int newRow, int newCol, char cell, boolean hasGearUp) {
        
        boolean isDiagonal = (currentRow!= newRow && currentCol!= newCol);
        if(cell=='G'){
            //System.out.println("added 5");
            return 5;
        }
        if(cell=='^'){
            return isDiagonal ? 10:5;
        }
        if(cell=='~'){
            if(!hasGearUp) return -1;
            return 3;
        }
        
        return 1;
    }
    private int Chevyshev(int startX,int startY,int goalX,int goalY){
            return(Math.max(Math.abs(startX - goalX), Math.abs(startY - goalY)));
    }
    protected  int huristic(int newRow, int newCol){
        int[] goal = _board.getGoal();
    
        int min = Chevyshev(newRow, newCol, goal[0], goal[1]);
        for(List<int[]> tunnels : _board.getunnels().values()){
            int[] side1 = tunnels.get(0);
            int[] side2 = tunnels.get(1);
            int direction1 = Chevyshev(newRow, newCol, side1[0], side1[1]) + 2 + Chevyshev(side2[0], side2[1], goal[0], goal[1]);
            int direction2 = Chevyshev(newRow, newCol, side2[0], side2[1]) + 2 + Chevyshev(side1[0], side1[1], goal[0], goal[1]);
            min = Math.min(min,Math.min(direction1, direction2));
        }
        return min;
    }

}
