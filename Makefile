# Makefile for compiling Java files

# Java compiler
JAVAC := javac
JAVA := java

# Flags for the Java compiler
JAVAC_FLAGS := -g -d bin -cp src

# Source directory
SRC_DIR := src

# Output directory for compiled classes
OUT_DIR := bin

# Rule to compile Java files into classes
compile:
	@echo Compiling $<
	$(JAVAC) $(JAVAC_FLAGS) src/datastructure/Node.java
	$(JAVAC) $(JAVAC_FLAGS) src/datahandling/Datahandling.java

	$(JAVAC) $(JAVAC_FLAGS) src/algorithm/UCS.java
	$(JAVAC) $(JAVAC_FLAGS) src/algorithm/GBFS.java
	$(JAVAC) $(JAVAC_FLAGS) src/algorithm/Algohandler.java

	$(JAVAC) $(JAVAC_FLAGS) src/gui/Gui.java
	$(JAVAC) $(JAVAC_FLAGS) src/App.java

# Executing the file
run: compile
	@echo Running...
	@echo.
	java -cp ${OUT_DIR} App