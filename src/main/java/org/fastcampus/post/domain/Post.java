package main.java.org.fastcampus.post.domain;

import main.java.org.fastcampus.common.domain.PositiveIntegerCounter;
import main.java.org.fastcampus.post.domain.contant.PostContent;
import main.java.org.fastcampus.post.domain.contant.PostPublicationState;
import main.java.org.fastcampus.user.domain.User;

public class Post {

    private final Long id;
    private final User author;
    //private final Long authorId;
    private final PostContent content;
    private final PositiveIntegerCounter likeCount;
    private PostPublicationState state;

    public Post(Long id, User author, PostContent content) {

        if(author==null) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.author = author;
        //this.authorId = author.getId();
        this.content = content;
        this.likeCount = new PositiveIntegerCounter();
        this.state = PostPublicationState.PUBLIC;
    }

    public void like (User user) {
        if(this.author.equals(user)) {
            throw  new IllegalArgumentException();
        }
        likeCount.increase();
    }

    public void unlike () {
        this.likeCount.decrease();
    }

    public void updatePost(User user, String updateContent, PostPublicationState state) {
        if(!this.author.equals(user)) {
            throw  new IllegalArgumentException();
        }
        this.state = state;
        this.content.updateContent(updateContent);
    }
}
