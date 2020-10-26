package Bots.commands;

import Bots.Command;
import Bots.Constants;
import Bots.tools.Tools;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserInfo implements Command {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(args.size() == 1) {
            String id = args.get(0).replaceAll("<@", "").replaceAll("!", "").replaceAll(">", "");
            Member target = event.getGuild().getMemberById(id);
            EmbedBuilder targetInfo = new EmbedBuilder()
                    .setTitle(target.getUser().getName() + " (" + target.getNickname() + ")")
                    .setAuthor(target.getUser().getName(), target.getUser().getAvatarUrl(), target.getUser().getEffectiveAvatarUrl())
                    .setDescription("**Mention**: " + target.getAsMention() + "\n**Tag**: " + target.getUser().getAsTag() + "\n**ID**: " + target.getId() + "\n**Avatar Link**: [link](" + target.getUser().getEffectiveAvatarUrl() + ")")
                    .setThumbnail(target.getUser().getEffectiveAvatarUrl())
                    .addField("Server Join Time", target.getTimeJoined().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), true)
                    .addField("Account Creation Date", target.getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), false);
            event.getChannel().sendMessage(targetInfo.build()).queue();
        } else {
            Tools.wrongUsage(event.getChannel(), this);
        }
    }

    @Override
    public String getCommand() {
        return "userinfo";
    }

    @Override
    public String getHelp() {
        return "Gives information about the user!\n" +
                "Usage: `" + Constants.TutorialBotPrefix + getCommand() + " <user>`";
    }
}
