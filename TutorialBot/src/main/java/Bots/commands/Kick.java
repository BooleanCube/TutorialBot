package Bots.commands;

import Bots.Command;
import Bots.Constants;
import Bots.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Kick implements Command {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() > 1) {
            long id = Long.parseLong(args.get(0).replaceAll("<@", "").replaceAll(">", "").replaceAll("!", ""));
            Member target = event.getGuild().getMemberById(id);
            Member moderator = event.getMember();
            if(!moderator.hasPermission(Permission.KICK_MEMBERS)) {
                event.getChannel().sendMessage("You don't have the permission to Kick Members!").queue();
                return;
            }
            if(!event.getGuild().getSelfMember().hasPermission(Permission.KICK_MEMBERS)) {
                event.getChannel().sendMessage("I don't have the **KICK_MEMBERS** Permission!").queue();
                return;
            }
            if(!event.getGuild().getSelfMember().canInteract(target) || target.hasPermission(Permission.KICK_MEMBERS)) {
                event.getChannel().sendMessage("I can't kick that user! The user has a higher role or is a moderator!").queue();
                return;
            }
            target.kick(args.size() == 1 ? "" : String.join(" ", args.subList(1, args.size()))).queue();
            event.getChannel().sendMessage("Successfully kicked " + target.getUser().getAsTag() + " from the server!").queue();
        } else {
            Tools.wrongUsage(event.getChannel(), this);
        }
    }

    @Override
    public String getCommand() {
        return "kick";
    }

    @Override
    public String getHelp() {
        return "Kicks a user off the server!\n" +
                "Usage: `" + Constants.TutorialBotPrefix + getCommand() + " <user> <reason>`";
    }
}
