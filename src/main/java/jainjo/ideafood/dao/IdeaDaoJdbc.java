package jainjo.ideafood.dao;

import jainjo.ideafood.model.Alimento;
import jainjo.ideafood.model.Foto;
import jainjo.ideafood.model.Idea;
import jainjo.ideafood.model.IngredienteItem;
import jainjo.ideafood.model.PuntoVenta;
import jainjo.ideafood.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class IdeaDaoJdbc implements IdeaDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IngredienteDao ingredienteDao;
    @Autowired
    private PuntoVentaDao puntoVentaDao;
    @Autowired
    private FotoDao fotoDao;
    private final static String INSERT_IDEA_SQL = "INSERT INTO Idea(AlimentoID, UserName, Porciones, Costo, Tiempo, FechaCreacion, Receta, PuntoVentaID) VALUES(?,?,?,?,?,?,?,?)";
    private final static String INSERT_INGREDIENTE_IDEA_SQL = "INSERT INTO Ingrediente_Idea (IngredienteID, IdeaID, Cantidad, Unidad) VALUES (?,?,?,?)";
    private final static String QUERY_RECENT_IDEAS_SQL = "";
    
    public IdeaDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final static RowMapper rowMapper = new RowMapper<Idea>() {
        @Override
        public Idea mapRow(ResultSet rs, int rowNum) throws SQLException {
            Idea idea = new Idea();
            idea.setId(rs.getLong("ID"));
            Alimento alimento = new Alimento();
            alimento.setNombre(rs.getString("Alimento"));
            idea.setAlimento(alimento);
            Usuario usuario = new Usuario();
            usuario.setUserName(rs.getString("UserName"));
            idea.setUsuario(usuario);
            idea.setPorciones(rs.getShort("Porciones"));
            idea.setCosto(rs.getDouble("Costo"));
            idea.setTiempo(rs.getShort("Tiempo"));
            idea.setFechaCreacion(rs.getTimestamp("FechaCreacion"));
            idea.setReceta(rs.getString("Receta"));
            PuntoVenta puntoVenta = new PuntoVenta();
            puntoVenta.setNombre(rs.getString("PuntoVenta"));
            idea.setPuntoVenta(puntoVenta);
            Foto foto = new Foto();
            foto.setUrl(rs.getString("Url"));
            foto.setUsuario(idea.getUsuario());
            foto.setIdea(idea);
            idea.setFotos(new ArrayList<Foto>());
            idea.getFotos().add(foto);
            return idea;
        }
    };
    
    @Override
    public void insert(final Idea idea) {
        PreparedStatementCreator psc;
        KeyHolder keyHolder;
        
        //Insertar PuntoVenta
        if(!idea.getPuntoVenta().getNombre().isEmpty()) 
            puntoVentaDao.insert(idea.getPuntoVenta());
        
        //Insertar Idea
        psc = new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(INSERT_IDEA_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, idea.getAlimento().getId());
                ps.setString(2, idea.getUsuario().getUserName());
                ps.setShort(3, idea.getPorciones());
                ps.setDouble(4, idea.getCosto());
                ps.setShort(5, idea.getTiempo());
                //ps.setTimestamp(6, new Timestamp(new Date().getTime() + TimeZone.getTimeZone("CST").getRawOffset()));
                ps.setTimestamp(6, new Timestamp(new Date().getTime()));
                if(idea.getReceta().isEmpty()) {
                    ps.setNull(7, java.sql.Types.VARCHAR);
                } else {
                    ps.setString(7, idea.getReceta());
                }
                if(idea.getPuntoVenta().getNombre().isEmpty()) {
                    ps.setNull(8, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(8, idea.getPuntoVenta().getId());
                }
                return ps;
            }
        };
        keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(psc,keyHolder);
        idea.setId(keyHolder.getKey().intValue());
        
        //Insertar Ingredientes
        if(idea.getReceta() != null && !idea.getReceta().isEmpty()) {
            for(final IngredienteItem i : idea.getIngredientes()) {
                ingredienteDao.insert(i.getIngrediente());
                
                psc = new PreparedStatementCreator(){
                    @Override
                    public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                        PreparedStatement ps = conn.prepareStatement(INSERT_INGREDIENTE_IDEA_SQL);
                        ps.setLong(1, i.getIngrediente().getId());
                        ps.setLong(2, idea.getId());
                        ps.setFloat(3, i.getCantidad());
                        ps.setString(4, i.getUnidad().getAbr().substring(0,i.getUnidad().getAbr().length()-1));
                        return ps;
                    }
                };
                jdbcTemplate.update(psc);
            }
        }
        
        //Insertar Foto
        if(!idea.getFotos().isEmpty())
            fotoDao.insert(idea.getFotos().get(0));
        
    }

    @Override
    public void update(Idea idea) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Idea find(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Idea> findRecent(int page, int pageSize) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_recentIdeas")
                .declareParameters(new SqlParameter("page", Types.INTEGER), new SqlParameter("pageSize",Types.INTEGER))
                .withoutProcedureColumnMetaDataAccess()
                .returningResultSet("data", rowMapper);
        simpleJdbcCall.compile();
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("page",page).addValue("pageSize", pageSize);
        return (ArrayList<Idea>) simpleJdbcCall.execute(paramMap).get("data");
    }
    
    @Override
    public List<Idea> findSuggestions(int page, int pageSize) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_suggestIdeas")
                .declareParameters(new SqlParameter("page", Types.INTEGER), new SqlParameter("pageSize",Types.INTEGER))
                .withoutProcedureColumnMetaDataAccess()
                .returningResultSet("data", rowMapper);
        simpleJdbcCall.compile();
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("page",page).addValue("pageSize", pageSize);
        return (ArrayList<Idea>) simpleJdbcCall.execute(paramMap).get("data");
    }
}