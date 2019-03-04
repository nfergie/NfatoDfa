/*
The main class for this project. It requires an input file where the nfa specifications are
This takes the nfa file and produces an output.dfa file with the corresponding dfa specifications.

Requires an arg of input file
 */
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
