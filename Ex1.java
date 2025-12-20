import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

            ///calc
            /// output
            

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
