import javax.imageio.IIOException;
import java.util.*;

public class Converter {

    public static void main(String[] args){
        Scanner sc = new Scanner("input.nfa");
        Nfa nfa = sc.createNfa();
        Dfa dfa = nfa.createDfa();
        for(State state : dfa.getStates()){
            System.out.println(state.name);
            System.out.println(state.transitionFunction);
        }
    }
}
