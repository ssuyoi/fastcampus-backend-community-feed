package org.fastcampus.message.application.interfaces;

import org.fastcampus.user.domain.User;

public interface MessageRepository {

    void sendLikeMessage(User sender, User targetUser);
}
