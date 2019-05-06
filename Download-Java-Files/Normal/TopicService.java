package service.implService;

import model.DTO.ErrorDTO;
import service.database.DAO.IDAO.ITopicDAO;
import service.database.DAO.implDAO.TopicDAO;
import model.DTO.SearchDTO;
import model.DTO.TopicDTO;
import service.IService.ITopicService;
import utils.constants.ErrorType;

public class TopicService implements ITopicService {

    @Override
    public TopicDTO getTopics(String tagID, String search_field, String page_num) {

        SearchDTO searchDTO = new SearchDTO();

        if (tagID == null) {
            tagID = "";
        }
        searchDTO.setTagId(tagID);

        if (search_field == null) {
            search_field = "";
        }

        searchDTO.setSearchInput(search_field);

        int pageNr;
        if (page_num == null) {
            pageNr = 1;
        } else {
            pageNr = Integer.parseInt(page_num);
        }
        searchDTO.setPageNumb(pageNr);

        ITopicDAO topicDAO = new TopicDAO();
        TopicDTO topicDTO = topicDAO.getTopicsFromDB(searchDTO);

        if (search_field == "") {
            topicDTO.setTabName("Stack Overflow - Where Developers Learn, Share, & Build Careers");
        } else {
            topicDTO.setTabName("Newest '" + search_field + "' Questions - Stack Overflow");
        }

        if (topicDTO.getErrorDTO() == null && topicDTO.getNumbOfRecords() == 0){
            String message = "No data found. Please use another search terms.";
            ErrorDTO errorDTO = new ErrorDTO(ErrorType.EMPTY_SEARCH_RESULT, message);
            topicDTO.setErrorDTO(errorDTO);
        }

        return topicDTO;
    }

}
