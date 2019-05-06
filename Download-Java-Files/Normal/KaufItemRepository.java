package hhu.ausleihservice.dataaccess;

import hhu.ausleihservice.databasemodel.KaufItem;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface KaufItemRepository extends ItemBaseRepository<KaufItem> {
}
