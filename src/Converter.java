import javax.imageio.IIOException;
import java.util.*;

public class Converter {

    public static void main(String[] args){
        Scanner sc = new Scanner("input.nfa");
        Nfa nfa = sc.createNfa();
        Dfa dfa = nfa.createDfa();
        dfa.dfaWriter();
    }
}
