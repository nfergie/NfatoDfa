import java.io.*;
import java.util.*;
import java.util.regex.*;
/*
This class is used to read the input NFA file and creates the NFA
 */


public class Scanner {
    private HashSet<String> states;
    private HashSet<String> language;
    private ArrayList<String> transFunctions;
    private String startState;
    private HashSet<String> acceptStates;

    //Reads the file and collects relevant data
    public Scanner(String filename){
        try{
            File f = new File(filename);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(f)));

            //regex for in between brackets
            Pattern p = Pattern.compile("\\{([^}]*)\\}");
            String line = reader.readLine();
            Matcher m = p.matcher(line);

            //get all of the states
            states = new HashSet<>();
            while(m.find()){
                String state = m.group().substring(1, m.group().length()-1);
                states.add(state);
            }

            //get the language
            line = reader.readLine();
            language = new HashSet<>(Arrays.asList(line.split("\\s+")));

            //get start state
            line = reader.readLine();
            m = p.matcher(line);
            while(m.find()){
                startState = m.group().substring(1, m.group().length()-1);
            }


            //get accept states
            line = reader.readLine();
            m = p.matcher(line);
            acceptStates = new HashSet<>();
            while(m.find()){
                String state = m.group().substring(1, m.group().length()-1);
                acceptStates.add(state);
            }

            //get transition strings;
            transFunctions = new ArrayList<>();
            while((line = reader.readLine()) != null){
                transFunctions.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Nfa createNfa(){
        HashMap<String, State> nfaStates = new HashMap<>();
        Nfa nfa = new Nfa();

        //add language to NFA
        nfa.setLanguage(language);

        //create states for NFA
        for(String state : states){
            State newState = new State();
            newState.addToName(state);

            //flag if accept
            if(acceptStates.contains(state)){
                newState.setIsAccept();
            }

            //flag if start
            if(startState.contains(state)){
                newState.setIsStart();
            }
            nfaStates.put(state, newState);
        }

        Pattern p = Pattern.compile("\\{([^}]*)\\}");
        Pattern q = Pattern.compile(",\\s(.*)=");

        //parse transition functions
        for(String trans : transFunctions){
            String state = "";
            String input = "";
            String dest = "";
            Matcher m = p.matcher(trans);
            Matcher n = q.matcher(trans);
            if (m.find())
            {
                state = m.group(1).replaceAll("\\s+","");;
            }
            if (n.find())
            {
                input = n.group(1).replaceAll("\\s+","");;
            }
            if (m.find())
            {
                dest = m.group(1).replaceAll("\\s+","");;
            }

            //add transition function to state
            if((state != null) && (input != null) && (dest != null)){
                State currState = nfaStates.get(state);
                State nextState = nfaStates.get(dest);
                currState.addTransition(input, nextState);
                nfaStates.put(state, currState);
            }
        }

        //add states to the nfa
        for(String state : nfaStates.keySet()){
            nfa.addState(nfaStates.get(state));
        }
        return nfa;
    }
}

