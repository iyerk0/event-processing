package com.karthik.persistence;

import java.net.UnknownHostException;

import org.springframework.stereotype.Repository;

import com.karthik.domain.Event;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

@Repository
public class EventDao {
  private MongoClient mongo;
  private DB db;
  private DBCollection table;
  private static final long oneHour = 60*60*1000;

  public EventDao() {
    try {
      mongo = new MongoClient("localhost", 27017);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    db = mongo.getDB("eventsdb");
    table = db.getCollection("event");
  }
  public void save(Event event) {
    BasicDBObject document = new BasicDBObject();
    document.put("name", event.name);
    document.put("time", event.time);
    table.insert(document);
  }
  public Integer query(String name, long time) {
    BasicDBObject query = new BasicDBObject("name", name)
    .append("time", new BasicDBObject("$gt", time-oneHour).append("$lt", time));
    return table.find(query).count();
  }

}
