package models.test.question.enums;

import javax.persistence.Entity;

import play.db.ebean.Model;


public class QuestionType extends Model {

    public static enum Type {
        OPEN, MULTIPLECHOICE, ONECHOICE
    }
  
}