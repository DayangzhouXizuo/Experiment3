package Experiment3.CS3;

public class BusinessLogic {
    public String processCommand(String command) {
        if (command.startsWith("ADD")) {
            return "Contact added";
        } else if (command.startsWith("MODIFY")) {
            return "Contact modified";
        } else if (command.startsWith("DELETE")) {
            return "Contact deleted";
        } else {
            return "Unknown command";
        }
    }
}