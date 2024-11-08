package my.simple.library.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Comment {
    private String id;
    private String authorName;
    private String commentText;
    private Integer bookId;
    private Integer authorId;

    @Override
    public String toString() {
        return "\n" + authorName + " :\n " + commentText;
    }
}
