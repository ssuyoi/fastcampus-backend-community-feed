package main.java.org.fastcampus.post.domain;

import main.java.org.fastcampus.common.domain.PositiveIntegerCounter;
import main.java.org.fastcampus.post.domain.contant.Content;
import main.java.org.fastcampus.post.domain.contant.PostContent;
import main.java.org.fastcampus.post.domain.contant.PostPublicationState;
import main.java.org.fastcampus.user.domain.User;

public class Post {

    private final Long id;
    private final User author;
    //private final Long authorId;
    private final Content content;
    private final PositiveIntegerCounter likeCount;
    private PostPublicationState state;

    public static Post createPost(Long id, User author, String content, PostPublicationState state) {
        return new Post(id, author, new PostContent(content), state);
    }

    public static Post createDefaultPost(Long id, User author, String content) {
        return new Post(id, author, new PostContent(content), PostPublicationState.PUBLIC);
    }

    public Post(Long id, User author, Content content, PostPublicationState state) {

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

    public int getLikeCount() {
        return likeCount.getCount();
    }

    public String getContent() {
        return content.getContentText();
    }
}
