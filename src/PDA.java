/**
 * Created by william on 6/15/15.
 */
import java.util.Stack;
public class PDA extends Automaton {
    Stack<Character> stack;
    char initialSymbol;
    char currentSymbol;



    public boolean evaluatePDA(String input){
        input+="E";
        stack = new Stack<>();
        State lastState =startState;
        Boolean accepted=false;
        setAlphabet(input);
        stack.push(initialSymbol);
        currentSymbol =initialSymbol;
        int count=0;

        Transition t =getNextTransition(lastState,input.charAt(0),currentSymbol);

        while (t!=null) {

            stack.pop();
            pushTokens(t.symbols);
            State next = t.destination;
            count++;
            if(count<input.length())
                t = getNextTransition(next, input.charAt(count),currentSymbol);
            else
                t = null;
            lastState = next;
        }

        if(lastState!=null) {

            for (State s : finalStates) {
                if (lastState.name.equals(s.name)) {
                    return true;
                }
            }
        }

        return accepted;

    }

    public void pushTokens(String input) {
        String[] tokens = input.split(",");

        String pushTokens = tokens[2] ;

        for(int i=pushTokens.length()-1;i>=0;i--) {
                if ( pushTokens.charAt(i) != 'E') {
                    stack.push(pushTokens.charAt(i));
                    currentSymbol = pushTokens.charAt(i);
                }else if(pushTokens.charAt(i) =='E')
                    currentSymbol =stack.lastElement();

        }
    }


    public void printStack(){
        for(Character c:stack)
            System.out.println(c.charValue());
    }


    public Transition getNextTransition(State start,char symbol, char topStack){
        Transition transition = null;
        for(Transition t: transitions){
            if(t.source.name.equals(start.name)){
                String tokens[] = t.symbols.split(",");
                if(tokens[0].charAt(0) == symbol && tokens[1].charAt(0) == topStack){
                    return t;
                }

            }
        }
        return transition;
    }
}
