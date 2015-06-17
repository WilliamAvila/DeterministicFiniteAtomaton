import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by william on 6/16/15.
 */
public class TMTape {

    private int currentPosition;
    private int size;
    private ArrayList<Character>  tapeContent;

    public TMTape(String input){
        tapeContent= new ArrayList<Character>();
        size =input.length();
        tapeContent.add('#');
        for(int i =1;i<=size;i++){
            tapeContent.add(input.charAt(i-1));
        }
        tapeContent.add('#');
        currentPosition=1;

    }

    public boolean MoveNext(String input){
        String[] tokens = input.split(",");

        tapeContent.set(currentPosition,tokens[1].charAt(0));


        if(tokens[2].charAt(0) == 'r')
            currentPosition++;
        else
            currentPosition--;



        return false;
    }


    public ArrayList<Character> getTapeContent(){
        return  tapeContent;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

}
