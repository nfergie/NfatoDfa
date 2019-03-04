JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Converter.java Scanner.java Nfa.java Dfa.java State.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
