package jainjo.ideafood.dao;

import com.mysql.cj.api.jdbc.Statement;
import jainjo.ideafood.model.Alimento;
import jainjo.ideafood.model.TipoAlimento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class AlimentoDaoJdbc implements AlimentoDao {
    @Autowired
    private TipoAlimentoDao tipoAlimentoDao;
    private JdbcTemplate jdbcTemplate;
    
    private final String SELECT_ALL_SQL = "SELECT * FROM Alimento";
    private final String INSERT_ALIMENTO_SQL = "INSERT INTO Alimento (Nombre, Descripcion) VALUES (?,?)";
    private final String INSERT_ALIMENTO_TIPO_SQL = "INSERT INTO Alimento_Tipo (AlimentoID, TipoID) VALUES (?,?)";
    
    private final static RowMapper rowMapper = new RowMapper<Alimento>() {
        @Override
        public Alimento mapRow(ResultSet rs, int rowNum) throws SQLException {
            Alimento alimento = new Alimento();
            alimento.setId(rs.getInt("ID"));
            alimento.setNombre(rs.getString("Nombre"));
            alimento.setDescripcion(rs.getString("Descripcion"));
            return alimento;
        }
    };
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void insert(final Alimento alimento) {
        PreparedStatementCreator psc = new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(INSERT_ALIMENTO_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, alimento.getNombre());
                ps.setString(2, alimento.getDescripcion());
                return ps;
            }
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(psc,keyHolder);
        alimento.setId(keyHolder.getKey().intValue());
        
        if(alimento.getTipos().size() > 0) {
            psc = new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                    String insertAlimentoTipoSql = "INSERT INTO Alimento_Tipo (AlimentoID, TipoID) VALUES ";
                    for (TipoAlimento tipo : alimento.getTipos()) {
                        insertAlimentoTipoSql += "(?,?), ";
                    }
                    insertAlimentoTipoSql = insertAlimentoTipoSql.substring(0, insertAlimentoTipoSql.length()-2);
                    PreparedStatement ps = conn.prepareStatement(insertAlimentoTipoSql, Statement.RETURN_GENERATED_KEYS);
                    for(int i = 0; i < alimento.getTipos().size(); i++) {
                        ps.setInt(i*2 + 1, alimento.getId());
                        ps.setShort(i*2 + 2, alimento.getTipos().get(i).getId());
                    }
                    return ps;
                }
            };
            
            jdbcTemplate.update(psc, keyHolder);
            
        }
    }

    @Override
    public void update(Alimento alimento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Alimento find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Alimento find(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Alimento> findAll() {
        List<Alimento> alimentos = jdbcTemplate.query(SELECT_ALL_SQL, this.rowMapper);
        for(Alimento a : alimentos) {
            a.setTipos(tipoAlimentoDao.find(a.getId()));
        }
        return alimentos;
    }
    
}
