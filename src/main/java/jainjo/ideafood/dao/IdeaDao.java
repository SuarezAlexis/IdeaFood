package jainjo.ideafood.dao;

import jainjo.ideafood.model.Idea;
import java.util.List;

public interface IdeaDao {
    public void insert(Idea idea);
    public void update(Idea idea);
    public void delete(long id);
    public Idea find(long id);
    public List<Idea> findRecent(int page, int pageSize);
    public List<Idea> findSuggestions(int page, int pageSize);
}
