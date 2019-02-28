import javax.imageio.IIOException;
import java.util.*;

public class Converter {

    public static void main(String[] args){
        Scanner sc = new Scanner("input.nfa");
        Nfa nfa = sc.createNfa();
        for(State state : nfa.getStates()){
            state.setEClosure();
        }
        Dfa dfa = nfa.createDfa();

        System.out.println(dfa.startStateToString());
    }
}
