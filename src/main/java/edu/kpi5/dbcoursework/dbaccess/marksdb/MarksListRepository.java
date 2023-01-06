package edu.kpi5.dbcoursework.dbaccess.marksdb;

import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarksListRepository extends MongoRepository<MarksList, String> {
}
