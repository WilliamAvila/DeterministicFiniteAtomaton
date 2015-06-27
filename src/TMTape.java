import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by william on 6/16/15.
 */
public class TMTape implements Serializable {

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
        tapeContent.add('#');
        currentPosition=1;

    }

    public int getSize() {
        return size;
    }

    public boolean MoveNext(String input){
        String[] tokens = input.split(",");
        boolean posMoved=false;

        if(tokens[0].charAt(0) == tapeContent.get(currentPosition)){
            tapeContent.set(currentPosition, tokens[1].charAt(0));


            if (tokens[2].charAt(0) == 'r')
                currentPosition++;
            else
                currentPosition--;
            posMoved=true;
        }else{
            posMoved=false;
        }
        return posMoved;
    }


    public String getTapeContent(){

        return  tapeContent.toString().replace(',', '\u0000').replace('[','\u0000').replace(']','\u0000');
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public char getCurrentTapeValue(){

        return tapeContent.get(this.currentPosition);
    }

}
