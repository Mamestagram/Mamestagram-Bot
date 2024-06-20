package net.mamesosu.Event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

public class RoleDistribution extends ListenerAdapter {

    private static final long CHANNELID = 1012691799350988810L;

    private static final long INFOROLEID = 1083976804680863744L;
    private static final long OSUCATCHID = 1115259320012111913L;
    private static final long OSUTAIKOID = 1115259058816041101L;
    private static final long OSUMANIAID = 1115258910123773992L;
    private static final long OSUSTDID = 1115258608557494333L;
    private static final long MULTIID = 1115260158830985246L;
    private static final long STREAMID = 1143545727394525214L;

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {

        if(e.getGuild().getIdLong() != 944248031136587796L) {
            return;
        }

        Role infoRole = e.getGuild().getRoleById(INFOROLEID);
        Role catchRole = e.getGuild().getRoleById(OSUCATCHID);
        Role taikoRole = e.getGuild().getRoleById(OSUTAIKOID);
        Role maniaRole = e.getGuild().getRoleById(OSUMANIAID);
        Role stdRole = e.getGuild().getRoleById(OSUSTDID);
        Role multiRole = e.getGuild().getRoleById(MULTIID);
        Role streamRole = e.getGuild().getRoleById(STREAMID);

        if(e.getChannel().getIdLong() == CHANNELID) {

            if(e.getReaction().getEmoji().equals(Emoji.fromUnicode("U+1F4E1"))){
               e.getGuild().addRoleToMember(e.getMember(), infoRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromFormatted("<:osu:1100702517119168562>"))) {
                e.getGuild().addRoleToMember(e.getMember(), stdRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromFormatted("<:fruits:1100702512681599089>"))) {
                e.getGuild().addRoleToMember(e.getMember(), catchRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromFormatted("<:mania:1100702514501910630>"))) {
                e.getGuild().addRoleToMember(e.getMember(), maniaRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromFormatted("<:taiko:1100702510152429588>"))) {
                e.getGuild().addRoleToMember(e.getMember(), taikoRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromUnicode("U+1F3AE"))) {
                e.getGuild().addRoleToMember(e.getMember(), multiRole).queue();
            }  else if(e.getReaction().getEmoji().equals(Emoji.fromUnicode("U+1F4F9"))) {
                e.getGuild().addRoleToMember(e.getMember(), streamRole).queue();
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent e) {

        if(e.getGuild().getIdLong() != 944248031136587796L) {
            return;
        }

        RestAction<Member> member = e.retrieveMember();

        Role infoRole = e.getGuild().getRoleById(INFOROLEID);
        Role catchRole = e.getGuild().getRoleById(OSUCATCHID);
        Role taikoRole = e.getGuild().getRoleById(OSUTAIKOID);
        Role maniaRole = e.getGuild().getRoleById(OSUMANIAID);
        Role stdRole = e.getGuild().getRoleById(OSUSTDID);
        Role multiRole = e.getGuild().getRoleById(MULTIID);
        Role streamRole = e.getGuild().getRoleById(STREAMID);

        if(e.getChannel().getIdLong() == CHANNELID) {

            if(e.getReaction().getEmoji().equals(Emoji.fromUnicode("U+1F4E1"))){
                e.getGuild().removeRoleFromMember(member.complete(), infoRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromFormatted("<:osu:1100702517119168562>"))) {
                e.getGuild().removeRoleFromMember(member.complete(), stdRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromFormatted("<:fruits:1100702512681599089>"))) {
                e.getGuild().removeRoleFromMember(member.complete(), catchRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromFormatted("<:mania:1100702514501910630>"))) {
                e.getGuild().removeRoleFromMember(member.complete(), maniaRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromFormatted("<:taiko:1100702510152429588>"))) {
                e.getGuild().removeRoleFromMember(member.complete(), taikoRole).queue();
            } else if(e.getReaction().getEmoji().equals(Emoji.fromUnicode("U+1F3AE"))) {
                e.getGuild().removeRoleFromMember(member.complete(), multiRole).queue();
            }  else if(e.getReaction().getEmoji().equals(Emoji.fromUnicode("U+1F4F9"))) {
                e.getGuild().removeRoleFromMember(member.complete(), streamRole).queue();
            }
        }
    }
}
