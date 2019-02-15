package jainjo.ideafood.dao;

import com.mysql.cj.api.jdbc.Statement;
import jainjo.ideafood.model.Foto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class FotoDaoJdbc implements FotoDao {
    private JdbcTemplate jdbcTemplate;
    private final String INSERT = "INSERT INTO Foto(IdeaID, Url, UserName) VALUES(?,?,?)";

    public FotoDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public FotoDaoJdbc() { 
    }
    
    @Override
    public void insert(final Foto foto) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, foto.getIdea().getId());
                ps.setString(2, foto.getUrl());
                if(foto.getUsuario().getUserName() == null)
                    ps.setNull(3, Types.VARCHAR);
                else
                    ps.setString(3, foto.getUsuario().getUserName());
                return ps;
            }
        };
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(psc,keyHolder);
        
        foto.setId(keyHolder.getKey().longValue());
    }

    @Override
    public void update(Foto foto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Foto find(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
