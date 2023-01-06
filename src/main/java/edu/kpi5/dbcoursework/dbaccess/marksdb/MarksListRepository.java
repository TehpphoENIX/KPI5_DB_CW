package edu.kpi5.dbcoursework.dbaccess.marksdb;

import edu.kpi5.dbcoursework.entities.marksdb.MarksList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MarksListRepository extends MongoRepository<MarksList, String> {
}
