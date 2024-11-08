package my.simple.library.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment {
    private Integer id;
    private String originalFileName;
    private String generatedFileName;
    private String contentType;
    private Double size;
    private String imageUrl;

    private Integer bookId;
}
