package api.iti0208;

import api.iti0208.data.entity.AppUser;
import api.iti0208.data.entity.Post;
import api.iti0208.repository.PostRepository;
import api.iti0208.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

@Component
public class SampleData implements CommandLineRunner {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public SampleData(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        userRepository.save(
                new AppUser("admin_test",
                        "$2a$10$WRCO20EAIVmqpeTcvOp9he1z/PlMvHuz2/i733ULbKchN7yt54I/m",
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                )
        );

        AppUser au = new AppUser("user_test",
                "$2a$10$05D4lwsoTRUR9lSDWkqkquPM56UZkiTPJ/cG5uK140GvYyqrGzr5u",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        userRepository.save(au);

        userRepository.save(
                new AppUser("user_hacks",
                        "$2a$10$x8gop7p3ZXKeqMUQLh/i8OsMDcZz9iLw8Cmx/UEhCpkhpZgoDG0Cy",
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );

        String filler = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has " +
                "been the industry's standard dummy text ever since the 1500s, when an unknown printer took a";

        Stream.of(
                Arrays.asList("Mathematics1", filler, "Mathematics"),
                Arrays.asList("Biology1", filler, "Biology"),
                Arrays.asList("Physics1", filler, "Physics"),
                Arrays.asList("Computer_Science0", filler, "Computer_Science"),
                Arrays.asList("Physics2", filler, "Physics"),
                Arrays.asList("Biology2", filler, "Biology"),
                Arrays.asList("Computer_Science1", filler, "Computer_Science"),
                Arrays.asList("Biology3", filler, "Biology"),
                Arrays.asList("Biology4", filler, "Biology"),
                Arrays.asList("Computer_Science2", filler, "Computer_Science"),
                Arrays.asList("Mathematics2", filler, "Mathematics"),
                Arrays.asList("Biology5", filler, "Biology"),
                Arrays.asList("Physics3", filler, "Physics"),
                Arrays.asList("Physics4", filler, "Physics"),
                Arrays.asList("Computer_Science3", filler, "Computer_Science"),
                Arrays.asList("Computer_Science4", filler, "Computer_Science"),
                Arrays.asList("Computer_Science5", filler, "Computer_Science"),
                Arrays.asList("Mathematics3", filler, "Mathematics"),
                Arrays.asList("Biology6", filler, "Biology"),
                Arrays.asList("Physics5", filler, "Physics"),
                Arrays.asList("Physics6", filler, "Physics"),
                Arrays.asList("Biology7", filler, "Biology"),
                Arrays.asList("Physics7", filler, "Physics"),
                Arrays.asList("Physics8", filler, "Physics"),
                Arrays.asList("Computer_Science6", filler, "Computer_Science"),
                Arrays.asList("Computer_Science7", filler, "Computer_Science"),
                Arrays.asList("Mathematics3", filler, "Mathematics"),
                Arrays.asList("Mathematics4", filler, "Mathematics"),
                Arrays.asList("Biology8", filler, "Biology"),
                Arrays.asList("Mathematics5", filler, "Mathematics"),
                Arrays.asList("Physics9", filler, "Physics"),
                Arrays.asList("Physics10", filler, "Physics"),
                Arrays.asList("Mathematics6", filler, "Mathematics"),
                Arrays.asList("Mathematics7", filler, "Mathematics"))
                .forEach(title -> postRepository.save(
                        new Post(title.get(0), title.get(2), title.get(1), au)
                ));

        //postRepository.findAll().forEach(System.out::println);

    }
}
