package jainjo.ideafood.dao;

import java.util.List;

import jainjo.ideafood.model.Usuario;

public interface UsuarioDao {
    public void insert(Usuario usuario);
    public void delete(String userName);
    public void update(Usuario usuario);
    public Usuario find(String userNameOrEmail);
    public List<Usuario> findHighScores(int top);
    public void setPasswordResetToken(String token, String email);
}
