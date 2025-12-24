import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Ex1 {
    private static List<String> readInput(String fileName) throws IOException{
        List<String> input = new ArrayList<>();
        try(BufferedReader br= new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = br.readLine()) != null){
                input.add(line);
            }
        }
        return input;
    }

    private static void writeOutput(String fileName,String path, int num, int maxSpace, int cost, double time, boolean showTime, boolean fountPath )throws IOException{
        try(PrintWriter output = new PrintWriter(new FileWriter(fileName))){
            output.println(path);
            output.println("Num: " + num);
            output.println("Max space: " + maxSpace);
            if(fountPath){
                output.println("Cost: " + cost);
            }
            else{
                output.println("Cost: inf");
            }

            if(showTime){
                output.printf("%.3f seconds", time);
            }
        }
    }

    public static void main(String[] args){
        String inputString = "input.txt";
        String outputString = "output.txt";
        try{
            List<String> input = readInput(inputString);
            
            String size = input.get(4).trim();
            String[] rowsAndCols = size.split("x");
            List<String> boardStrings = input.subList(5, input.size());// start at index 5 until the end

            String algo = input.get(0).trim();
            String side = input.get(1).trim();
            String time = input.get(2).trim();
            String open = input.get(3).trim();
            int rows = Integer.parseInt(rowsAndCols[0]);
            int cols = Integer.parseInt(rowsAndCols[1]);
            Board board = new Board(rows, cols, boardStrings);
           
            Algorithm solver = null;
            if (algo.contains("BFS")) {
                solver = new BFS(board, side); 
            } else if (algo.contains("DFID")) {
                solver = new DFID(board, side);
            }else if (algo.contains("A*")) {
                solver = new AStar(board, side);
            }else if (algo.contains("IDA*")) {
                solver = new IDAStar(board, side);
            }else if (algo.contains("DFBnB")) {
                solver = new DFBnB(board, side);
            }
             System.out.println("Tie Breaker Mode: " + (Node.compareNewFirst ? "New-First (LIFO)" : "Old-First (FIFO)"));
            long startTime = System.currentTimeMillis();
            Node result = solver.solve();
            long endTime = System.currentTimeMillis();
            double totalTimeSeconds = (endTime - startTime) / 1000.0;
            boolean showTime = time.contains("with time");

            // Process Results
            String pathString = getPath(result);
            boolean foundPath = (result != null);
            int cost = foundPath ? result.getG() : 0;
            int numNodes = solver.getSumOfNodes();

            int maxSpace = 0;
            // if (solver instanceof BFS) {
            //     maxSpace = ((BFS) solver).getMaxSpace();
            // }
            maxSpace = solver.getMaxSpace();

            if (algo.contains("BFS")) {
                solver = new BFS(board, side); 
            }
            writeOutput(outputString, pathString, numNodes, maxSpace, cost, totalTimeSeconds, showTime, foundPath);
            ///calc
            /// output
            

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private static String getPath(Node node) {
        if (node == null) return "no path";
        
        StringBuilder sb = new StringBuilder();
        Node current = node;
        
        while (current.getParent() != null) {
            // Insert action at the beginning (reverse order)
            if (sb.length() > 0) {
                sb.insert(0, "-");
            }
            sb.insert(0, current.getAction());
            current = current.getParent();
        }
        return sb.toString();
    }
}
