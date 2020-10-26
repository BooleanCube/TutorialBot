package Bots.commands;

import Bots.Command;
import Bots.Constants;
import Bots.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Ban implements Command {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() < 1) {
            Tools.wrongUsage(event.getChannel(), this);
        } else {
            long targetID = Long.parseLong(args.get(0).replaceAll("<@", "").replaceAll(">", "").replaceAll("!", ""));
            Member target = event.getGuild().getMemberById(targetID);
            if(target == null) {
                event.getChannel().sendMessage("This user was not found in this server!").queue();
            }
            Member moderator = event.getMember();
            //Remember to check if the modertor canInteract with the target as well!
            if(!moderator.hasPermission(Permission.BAN_MEMBERS) || !moderator.canInteract(target)) {
                event.getChannel().sendMessage("You don't have the permission to Ban members!").queue();
                return;
            }
            if(!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("I don't have the **BAN_MEMBERS** permission!").queue();
                return;
            }
            if(target.hasPermission(Permission.BAN_MEMBERS) || !event.getGuild().getSelfMember().canInteract(target)) {
                event.getChannel().sendMessage("I can't ban this user because the user has a higher role than me or is a moderator!").queue();
                return;
            }
            int days = 0;
            String reason = "";
            try {
                days = Integer.parseInt(args.get(1));
            } catch(Exception e) {}
            try {
                if(days == 0) {
                    reason = String.join(" ", args.subList(1, args.size()));
                } else {
                    reason = String.join(" ", args.subList(2, args.size()));
                }
            } catch(Exception e) {}
            try {
                target.ban(days == 0 ? days : 0, reason == "" ? null : reason).queue();
                event.getChannel().sendMessage("Successfully banned " + target.getAsMention()).queue();
            } catch(Exception e) {
                event.getChannel().sendMessage("Could not successfully ban " + target.getAsMention()).queue();
            }
        }
    }

    @Override
    public String getCommand() {
        return "ban";
    }

    @Override
    public String getHelp() {
        return "Bans a user off the server!\n" +
                "Usage: `" + Constants.TutorialBotPrefix + getCommand() + " <user> <time> <reason>`";
    }
}
