package Bots.reactions;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class FriendlyGreeting extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        if(event.getMessageIdLong() == 741051604786216970l) {
            if(event.getReactionEmote().getEmoji().equalsIgnoreCase("\uD83D\uDC4B")) {
                event.getChannel().sendMessage("Hello There! This is a friendly greeting!").queue();
            }
        }
    }
}
