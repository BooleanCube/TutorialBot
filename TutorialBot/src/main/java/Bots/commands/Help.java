package Bots.commands;

import Bots.Command;
import Bots.Constants;
import Bots.Manager;
import Bots.tools.Tools;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Help implements Command {
    public final Manager manager;

    public Help(Manager m) {
        this.manager = m;
    }

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() > 1) {
            Tools.wrongUsage(event.getChannel(), this);
            return;
        }
        if(args.isEmpty()) {
            EmbedBuilder e = new EmbedBuilder()
                    .setTitle("A list of all my commands:");
            StringBuilder desc = e.getDescriptionBuilder();
            manager.getCommands().forEach(command -> {
                desc.append("`").append(command.getCommand()).append("`\n");
            });
            event.getChannel().sendMessage(e.build()).queue();
            return;
        }
        Command command = manager.getCommand(String.join("", args));
        if(command == null) {
            event.getChannel().sendMessage("The command `" + String.join("", args) + "` does not exist!\n" +
                    "Use `" + Constants.TutorialBotPrefix + command.getCommand() + "` for a list of all my commands!").queue();
            return;
        }
        event.getChannel().sendMessage("Command help for `" + command.getCommand() + "`\n" +
                command.getHelp()).queue();
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Gives you a list of existing commands on TutorialBot!\n" +
                "Usage: `" + Constants.TutorialBotPrefix + getCommand() + " <command>`";
    }
}