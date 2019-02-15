package jainjo.ideafood.dao;

import jainjo.ideafood.model.Ingrediente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class IngredienteDaoJdbc implements IngredienteDao {
    private JdbcTemplate jdbcTemplate;
    
    private final static RowMapper rowMapper = new RowMapper<Ingrediente>() {
        @Override
        public Ingrediente mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setId(rs.getShort("ID"));
            ingrediente.setNombre(rs.getString("Nombre"));
            ingrediente.setDescripcion(rs.getString("Descripcion"));
            return ingrediente;
        }
    };
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void insert(Ingrediente ingrediente) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("UPSERT_INGREDIENTE")
                .declareParameters(new SqlOutParameter("ID", Types.BIGINT), new SqlParameter("p_Nombre", Types.VARCHAR))
                .withoutProcedureColumnMetaDataAccess();
        simpleJdbcCall.compile();
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("p_Nombre", ingrediente.getNombre());
        ingrediente.setId(simpleJdbcCall.executeFunction(Long.class, paramMap));
    }

    @Override
    public void update(Ingrediente ingrediente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ingrediente find(short id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ingrediente find(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ingrediente> findAll() {
        return jdbcTemplate.query("SELECT * FROM Ingrediente", rowMapper);
    }
    
}
