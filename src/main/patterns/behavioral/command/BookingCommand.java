package patterns.behavioral.command;

public interface BookingCommand {
    void execute();
    void undo();
}
