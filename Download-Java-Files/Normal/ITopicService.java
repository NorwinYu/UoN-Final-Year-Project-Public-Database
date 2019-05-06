package service.IService;

import model.DTO.TopicDTO;

public interface ITopicService {

    TopicDTO getTopics(String tagID, String search_field, String page_num);

}
