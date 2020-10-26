package Bots.commands;

import Bots.Command;
import Bots.Constants;
import Bots.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class Unban implements Command {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            Tools.wrongUsage(event.getChannel(), this);
        } else {
            String strmember = String.join(" ", args);
            Member moderator = event.getMember();
            if(!moderator.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("You don't have the permission to Unban members!").queue();
                return;
            }
            if(!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("I don't have the **BAN_MEMBERS** permission!").queue();
                return;
            }
            //Checks through the ban list and finds possible targets given out arguments of the command and bans the first possible target in our list
            //Our arguments now don't only have to be userID but now they can be userTag or userName
            event.getGuild().retrieveBanList().queue((bans) -> {
                //Finds all possible targets
                List<User> possibleTargets = bans.stream().filter((ban) -> isRightUser(ban, strmember)).map(Guild.Ban::getUser).collect(Collectors.toList());
                if (possibleTargets.isEmpty()) {
                    event.getChannel().sendMessage("This user is not banned").queue();
                    return;
                }
                //Unbans the first possible target found!
                User target = possibleTargets.get(0);
                String targetName = String.format("%#s", target);
                event.getGuild().unban(target).queue();
                event.getChannel().sendMessage("User " + targetName + " unbanned.").queue();
            });
        }
    }

    @Override
    public String getCommand() {
        return "unban";
    }

    @Override
    public String getHelp() {
        return "Unbans a user from the server!\n" +
                "Usage: `" + Constants.TutorialBotPrefix + getCommand() + " <user>`";
    }

    //This method checks if the given user matches with our given arguments. All possible ways are allowed: ID, Tag, Username
    //We can't unban a user by pinging them though!
    private static boolean isRightUser(Guild.Ban ban, String args) {
        User bannedUser = ban.getUser();
        return bannedUser.getName().equalsIgnoreCase(args) || bannedUser.getId().equals(args)
                || bannedUser.getAsTag().equalsIgnoreCase(args);
    }
}
