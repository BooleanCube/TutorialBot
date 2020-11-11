package Bots.commands;

import Bots.Command;
import Bots.Constants;
import Bots.tools.Tools;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class Roast implements Command {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if(args.isEmpty()) {
            try {
                URL api = new URL("https://api.snowflakedev.xyz/roast");
                HttpsURLConnection con = (HttpsURLConnection) api.openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String input = bf.readLine();
                String roast = input.substring(10, input.length()-2);
                event.getChannel().sendMessage(roast).queue();
            } catch (Exception e) {
                event.getChannel().sendMessage("**An error occurred!** Please try again later!").queue();
            }
        } else Tools.wrongUsage(event.getChannel(), this);
    }

    @Override
    public String getCommand() {
        return "roast";
    }

    @Override
    public String getHelp() {
        return "Roasts the user that uses this command!\nUsage: `" + Constants.TutorialBotPrefix + getCommand() + "`";
    }
}
