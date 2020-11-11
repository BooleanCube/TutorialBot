package Bots;

import Bots.commands.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;
import java.util.regex.Pattern;

public class Manager {

    private final Map<String, Command> commands = new HashMap<>();

    Manager() {
        addCommand(new Ping());
        addCommand(new Help(this));
        addCommand(new Meme());
        addCommand(new Kick());
        addCommand(new Ban());
        addCommand(new Unban());
        addCommand(new Purge());
        addCommand(new Poll());
        addCommand(new UserInfo());
        addCommand(new EightBall());
        addCommand(new Roast());
    }

    private void addCommand(Command c) {
        if(!commands.containsKey(c.getCommand())) {
            commands.put(c.getCommand(), c);
        }
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

    public Command getCommand(String commandName) {
        if(commandName == null) {
            return null;
        }
        return commands.get(commandName);
    }

    void run(GuildMessageReceivedEvent event) {
        final String msg = event.getMessage().getContentRaw();
        if(!msg.startsWith(Constants.TutorialBotPrefix)) {
            return;
        }
        final String[] split = msg.replaceFirst("(?i)" + Pattern.quote(Constants.TutorialBotPrefix), "").split("\\s+");
        final String command = split[0].toLowerCase();
        if(commands.containsKey(command)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);
            commands.get(command).run(args, event);
        }
    }

}
