package jainjo.ideafood.dao;

import jainjo.ideafood.model.TipoAlimento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class TipoAlimentoDaoJdbc implements TipoAlimentoDao {
    private JdbcTemplate jdbcTemplate;
    
    private final String SELECT_ALL_SQL = "SELECT * FROM TipoAlimento";
    private final String SELECT_FROM_ALIMENTO_SQL = "SELECT TA.* FROM TipoAlimento TA JOIN Alimento_Tipo A_T ON (TA.ID = A_T.TipoID) WHERE A_T.AlimentoID = ?";
    
    private final static RowMapper rowMapper = new RowMapper<TipoAlimento>() {
        @Override
        public TipoAlimento mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoAlimento tipoAlimento = new TipoAlimento();
            tipoAlimento.setId(rs.getShort("ID"));
            tipoAlimento.setTipo(rs.getString("Tipo"));
            tipoAlimento.setDescripcion(rs.getString("Descripcion"));
            return tipoAlimento;
        }
    };
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(TipoAlimento tipoAlimento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(TipoAlimento tipoAlimento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(short id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoAlimento find(short id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoAlimento find(String tipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<TipoAlimento> find(int alimentoId) {
        return jdbcTemplate.query(SELECT_FROM_ALIMENTO_SQL, new Object[] {alimentoId}, rowMapper);
    }
    
    @Override
    public List<TipoAlimento> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, rowMapper);
    }
    
}
