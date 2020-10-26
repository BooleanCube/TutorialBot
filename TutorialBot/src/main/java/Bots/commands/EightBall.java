package Bots.commands;

import Bots.Command;
import Bots.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class EightBall implements Command {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        String[] replies = {"Yes!", "No!", "Maybe...", "Probably", "Probably not...", "Absolutely!", "Sure!", "Definitely not!", "false", "true"};
        event.getChannel().sendMessage(new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                .addField(String.join(" ", args), replies[(int)(Math.random()*replies.length)], true)
                .build()
        ).queue();
    }

    @Override
    public String getCommand() {
        return "8ball";
    }

    @Override
    public String getHelp() {
        return "Ask a question and receive the answer!\nUsage: `" + Constants.TutorialBotPrefix + getCommand() + " <question>`";
    }
}
