JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Card.java \
	CardTestOne.java \
	Player.java \
	Deck.java \
	DeckTestOne.java \
	PlayerTestOne.java \
	Mahogany.java \
	GameClient.java \
	GameServer.java \
	TheGame.java \
	EgyptianWar.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class