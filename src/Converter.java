import javax.imageio.IIOException;
import java.util.*;

public class Converter {

    public static void main(String[] args){
        if (args.length > 0) {
            Scanner sc = new Scanner(args[0]);
            Nfa nfa = sc.createNfa();
            Dfa dfa = nfa.createDfa();
            dfa.dfaCleanup();
            dfa.dfaWriter();
        }else{
            System.out.println("Program requires an input file as an argument");
        }

    }
}
