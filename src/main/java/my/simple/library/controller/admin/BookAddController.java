package my.simple.library.controller.admin;

import my.simple.library.model.Attachment;
import my.simple.library.model.Book;
import my.simple.library.repository.AttachmentRepository;
import my.simple.library.repository.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Controller
public class BookAddController {

    private final AttachmentRepository attachmentRepository;
    private final BookRepository bookRepository;

    public BookAddController(AttachmentRepository attachmentRepository, BookRepository bookRepository) {
        this.attachmentRepository = attachmentRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/app/admin/book-list/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addBook() {
        return "admin/book_add";
    }

    @PostMapping("/app/admin/book-list/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addBook(@RequestParam(required = false) String title,
                                @RequestParam(required = false) String author,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) MultipartFile file,
                                @RequestParam(required = false) MultipartFile image) {

        ModelAndView modelAndView = new ModelAndView();

        System.out.println(title);
        System.out.println(author);
        System.out.println(description);
        System.out.println(file);
        System.out.println(image);

        if (title==null || title.isBlank()){
            modelAndView.addObject("title_error", "Invalid title");
            modelAndView.setViewName("admin/book_add");
            return modelAndView;
        }
        if (author==null || author.isBlank()){
            modelAndView.addObject("author_error", "Invalid author");
            modelAndView.setViewName("admin/book_add");
            return modelAndView;
        }
        if (description==null || description.isBlank()){
            modelAndView.addObject("description_error", "Invalid description");
            modelAndView.setViewName("admin/book_add");
            return modelAndView;
        }
        if (file==null){
            modelAndView.addObject("file_error", "Invalid file");
            modelAndView.setViewName("admin/book_add");
            return modelAndView;
        }
        if (image==null || !Objects.equals(StringUtils.getFilenameExtension(image.getOriginalFilename()), "jpg")){
            modelAndView.addObject("image_error", "Invalid image");
            modelAndView.setViewName("admin/book_add");
            return modelAndView;
        }

        String imageUrl = null;

        try {
            String originalImageName = image.getOriginalFilename();
            String generatedImageName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalImageName);

            InputStream inputStream = image.getInputStream();
            Path imagePath = Path.of("C:/Users/buriy/IdeaProjects/my_simple_library/src/main/resources/images/" + generatedImageName);
            Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);

            imageUrl = imagePath.toFile().getPath();

            System.out.println(imageUrl);

        }catch (IOException e){
            e.printStackTrace();
        }


        String originalFileName = file.getOriginalFilename();
        String generatedFileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFileName);
        String contentType = file.getContentType();
        double size = file.getSize();


        Book book = Book.builder()
                .title(title)
                .author(author)
                .description(description)
                .build();

        Integer bookId = bookRepository.save(book);

        Attachment attachment = Attachment.builder()
                .originalFileName(originalFileName)
                .generatedFileName(generatedFileName)
                .contentType(contentType)
                .size(size)
                .imageUrl(imageUrl)
                .bookId(bookId)
                .build();


        Integer attachmentId = attachmentRepository.save(attachment);

        try {
            Path path = Path.of("C:/Users/buriy/IdeaProjects/my_simple_library/src/main/resources/files/" + generatedFileName);
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e) {
            e.printStackTrace();
        }

        modelAndView.setViewName("admin/book_list");
        return modelAndView;
    }
}
