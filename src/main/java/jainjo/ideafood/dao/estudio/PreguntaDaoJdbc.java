/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jainjo.ideafood.dao.estudio;

import jainjo.ideafood.model.estudio.Pregunta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author alexis.suarez
 */
public class PreguntaDaoJdbc implements PreguntaDao {
    private JdbcTemplate jdbcTemplate;
    
    private static final String INSERT_PREGUNTA_SQL = "INSERT INTO Pregunta(Pregunta,IncisoA,IncisoB,IncisoC,IncisoD,Respuesta) VALUES(?,?,?,?,?,?)";
    
    public PreguntaDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final static RowMapper rowMapper = new RowMapper<Pregunta>() {
        @Override
        public Pregunta mapRow(ResultSet rs, int i) throws SQLException {
            Pregunta pregunta = new Pregunta();
            pregunta.setId(rs.getInt("ID"));
            pregunta.setIncisoA(rs.getString("IncisoA"));
            pregunta.setIncisoB(rs.getString("IncisoB"));
            pregunta.setIncisoC(rs.getString("IncisoC"));
            pregunta.setIncisoD(rs.getString("IncisoD"));
            pregunta.setRespuesta(rs.getString("Respuesta").charAt(0));
            return pregunta;
        }
    };
    
    @Override
    public void insert(final Pregunta pregunta) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(INSERT_PREGUNTA_SQL);
                ps.setString(1,pregunta.getPregunta());
                ps.setString(2,pregunta.getIncisoA());
                ps.setString(3,pregunta.getIncisoB());
                ps.setString(4,pregunta.getIncisoC());
                ps.setString(5,pregunta.getIncisoD());
                ps.setString(6,String.valueOf(pregunta.getRespuesta()));
                return ps;
            }
        };
        jdbcTemplate.update(psc);
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Pregunta pregunta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pregunta find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pregunta> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
