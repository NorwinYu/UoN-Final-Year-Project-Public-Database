package api.iti0208.mapper;

import api.iti0208.data.entity.AppUser;
import api.iti0208.data.entity.Post;
import api.iti0208.data.entity.Reply;
import api.iti0208.data.output.PostDetails;
import api.iti0208.data.output.PostOverview;
import api.iti0208.data.output.ReplyDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class EntityToOutputObjectMapper {

    public List<PostOverview> postListToPostOverviewList(List<Post> posts) {
        return posts.stream().map(this::postToPostOverview).collect(toList());
    }

    private PostOverview postToPostOverview(Post post) {
        PostOverview postOverview = new PostOverview();
        postOverview.setId(post.getId());
        postOverview.setTitle(post.getTitle());
        postOverview.setTopic(post.getTopic());
        postOverview.setDescription(post.getDescription());
        postOverview.setPostedAt(post.getPostedAt());
        postOverview.setPostedBy(post.getPostedBy().getUsername());

        return postOverview;
    }

    public PostDetails postToPostDetails(Post post, AppUser currentUser) {
        PostDetails postDetails = new PostDetails();
        postDetails.setId(post.getId());
        postDetails.setTitle(post.getTitle());
        postDetails.setTopic(post.getTopic());
        postDetails.setDescription(post.getDescription());
        postDetails.setRewardDescription(post.getRewardDescription());
        postDetails.setFileLocation(post.getFileLocation());
        postDetails.setPostedAt(post.getPostedAt());
        postDetails.setPostedBy(post.getPostedBy().getUsername());

        if (currentUser != null) {
            if (currentUser.getUsername().equals(postDetails.getPostedBy()) ||
                    currentUser.getGrantedAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                postDetails.setCanDelete(true);
            }
        } else {
            postDetails.setCanDelete(false);
        }

        List<ReplyDetails> replyDetails = post.getAnswers().stream()
                .map(reply -> replyToReplyDetails(reply, currentUser)).collect(toList());
        postDetails.setReplies(replyDetails);

        return postDetails;
    }


    public ReplyDetails replyToReplyDetails(Reply reply, AppUser currentUser) {
        ReplyDetails replyDetails = new ReplyDetails();
        replyDetails.setId(reply.getId());
        replyDetails.setReply(reply.getReply());
        replyDetails.setFileLocation(reply.getFileLocation());
        replyDetails.setPostedAt(reply.getPostedAt());
        replyDetails.setPostedBy(reply.getPostedBy().getUsername());

        if (currentUser != null) {
            if (currentUser.getUsername().equals(replyDetails.getPostedBy())) {
                replyDetails.setCanDelete(true);
            }
            if (currentUser.getGrantedAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                replyDetails.setCanDelete(true);
            }
        } else {
            replyDetails.setCanDelete(false);
        }

        return replyDetails;
    }
}
