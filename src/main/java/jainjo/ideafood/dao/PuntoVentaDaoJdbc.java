package jainjo.ideafood.dao;

import jainjo.ideafood.model.PuntoVenta;
import java.sql.Types;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


public class PuntoVentaDaoJdbc implements PuntoVentaDao {
    private final JdbcTemplate jdbcTemplate;

    public PuntoVentaDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void insert(final PuntoVenta pv) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("UPSERT_PUNTOVENTA")
                .declareParameters(new SqlOutParameter("ID", Types.INTEGER), new SqlParameter("p_Nombre", Types.VARCHAR))
                .withoutProcedureColumnMetaDataAccess();
        simpleJdbcCall.compile();
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("p_Nombre", pv.getNombre());
        pv.setId(simpleJdbcCall.executeFunction(Integer.class, paramMap));
    }

    @Override
    public void update(PuntoVenta pv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PuntoVenta find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PuntoVenta> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
