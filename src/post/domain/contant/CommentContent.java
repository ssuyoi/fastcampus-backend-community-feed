package post.domain.contant;

public class CommentContent extends Content {

    private static final int MAX_COMMENT_LENGTH = 100;

    public CommentContent(String content) {
        super(content);
    }

    protected void checkText(String contentText) {
        if(contentText == null || contentText.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if(MAX_COMMENT_LENGTH < contentText.length()) {
            throw new IllegalArgumentException();
        }
    }


}
