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
    
    private static final String INSERT_PREGUNTA_SQL = "INSERT INTO Pregunta(Pregunta,IncisoA,IncisoB,IncisoC,IncisoD,Respuesta,Unidad,Materia) VALUES(?,?,?,?,?,?,?,?)";
    
    public PreguntaDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final static RowMapper rowMapper = new RowMapper<Pregunta>() {
        @Override
        public Pregunta mapRow(ResultSet rs, int i) throws SQLException {
            Pregunta pregunta = new Pregunta();
            pregunta.setId(rs.getInt("ID"));
            pregunta.setPregunta(rs.getString("Pregunta"));
            pregunta.setIncisoA(rs.getString("IncisoA"));
            pregunta.setIncisoB(rs.getString("IncisoB"));
            pregunta.setIncisoC(rs.getString("IncisoC"));
            pregunta.setIncisoD(rs.getString("IncisoD"));
            pregunta.setRespuesta(rs.getString("Respuesta").charAt(0));
            pregunta.setUnidad(rs.getInt("Unidad"));
            pregunta.setMateria(rs.getString("Materia"));
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
                ps.setInt(7,pregunta.getUnidad());
                ps.setString(8,pregunta.getMateria());
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
        List<Pregunta> preguntas = jdbcTemplate.query("SELECT * FROM Pregunta WHERE ID = " + id, rowMapper);
        return preguntas.size() > 0 ? preguntas.get(0) : new Pregunta();
    }

    @Override
    public List<Pregunta> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Pregunta> find(String materia, int unidad, String texto) {
        String query = "SELECT * FROM Pregunta";
        if(texto == null || texto.isEmpty()) {
            if(materia == null || materia.isEmpty() || materia.equals("Todas")) {
                return findAll();
            } else {
                query += " WHERE Materia = '" + materia + "'";
                if(unidad > 0) query += " AND Unidad = " + unidad;
            }
        } else {
            query += " WHERE Pregunta LIKE '%" + texto + "%'";
            if(materia != null && !materia.isEmpty() && !materia.equals("Todas")) {
                query += " AND Materia = '" + materia + "'";
                if(unidad > 0) query += " AND Unidad = " + unidad;
            }
        }
        return jdbcTemplate.query(query, rowMapper);
    }
    
}
