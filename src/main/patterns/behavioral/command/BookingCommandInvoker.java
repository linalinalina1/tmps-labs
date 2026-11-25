package patterns.behavioral.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class BookingCommandInvoker {
    private final Deque<BookingCommand> history = new ArrayDeque<>();

    public void execute(BookingCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Command is required.");
        }

        command.execute();
        history.push(command);
    }

    public boolean undoLast() {
        if (history.isEmpty()) {
            return false;
        }

        BookingCommand command = history.peek();
        command.undo();
        history.pop();
        return true;
    }
}
