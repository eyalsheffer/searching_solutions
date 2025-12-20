import java.util.Objects;

public class Node implements Comparable<Node>{// implement for an option to override the comparison method

    private final int _row;
    private final int _col;
    private final boolean _hasGearUp;
    private final Node _parent;
    private final String _action;//how did we get here

    //heurstic purpuses 
    private int _g;
    private int _h;
    private int _f;

    //tie breakers
    private final int _creationTime;
    private static int _globalCounter = 0;

    public Node(int row, int col, boolean hasGearUp, Node parent, String action, int g, int h){
        this._row = row;
        this._col = col;
        this._hasGearUp = hasGearUp;
        this._parent = parent;
        this._action = action;
        this._g = g;
        this._h = h;
        this._f = g+h;
        this._creationTime = ++_globalCounter;
    }

    public int getRow(){return _row;}
    public int getCol(){return _col;}
    public boolean hasGearUp(){return _hasGearUp;}
    public Node getParent(){return _parent;}
    public String getAction(){return _action;}
    public int getG(){return _g;}
    public int getH(){return _h;}
    public int getF(){return _f;}
    public int getCreationTime(){return _creationTime;}

    @Override
    public boolean equals(Object o){
        if(this==o)
            return true;
        if(o==null || getClass()!= o.getClass())
            return false;
        Node other = (Node) o;
        return (_row==other._row && _col==other._col && _hasGearUp== other._hasGearUp);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(_row, _col, _hasGearUp);
    }
    
    @Override
    public int compareTo(Node other){//for priority queue
        return Double.compare(this._f, other._f);
    }
    public static void resetGlobalCounter(){
        _globalCounter = 0;
    }
}
