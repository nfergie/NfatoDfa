import javax.imageio.IIOException;
import java.util.*;

public class Converter {

    public static void main(String[] args){
        Scanner sc = new Scanner("input.nfa");
        Nfa nfa = sc.createNfa();
        for(State state : nfa.getStates()){
            System.out.println("State: " + state.name);
            System.out.println("Memadd: " + state);
            System.out.println(state.transitionFunction);
            for(String input : nfa.getLanguage()){
                System.out.println(input);
                System.out.println(nfa.nextStates(input, state));
            }
        }
    }
}
