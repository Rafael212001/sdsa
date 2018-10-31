package br.com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.entities.CD;
import br.com.entities.Disciplina;
import br.com.entities.Professor;
import br.com.jdbc.ConnectionDB;

public class professorDAO {
	Connection con;

	public professorDAO() {
		con = ConnectionDB.getConnection();
	}

	public boolean inserir(Professor p) {
		String sql = "INSERT INTO Colaboradores (nome, carga_hora, tipo)"
				+ "VALUES (?,?,?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, p.getNome());
			ps.setInt(2, p.getCarga_hora());
			ps.setInt(3, p.getTipo());

			if (ps.executeUpdate() > 0) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return false;
	}
	
	public boolean inserirCD(CD cd) {
		String sql = "INSERT INTO CD (id_colaborador, id_disciplina)"
				+ "VALUES (?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, cd.getId_colaborador());
			ps.setInt(2, cd.getId_disciplina());
			
			if(ps.executeUpdate() > 0) {
				return true;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public List<Professor> listarTodos() {
		List<Professor> list = new ArrayList<Professor>();
		String sql = "SELECT * FROM Colaboradores ";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Professor p = new Professor();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setCarga_hora(rs.getInt("carga_hora"));
				p.setRestante(rs.getInt("restante"));
				p.setTipo(rs.getInt("tipo"));
				p.setFoto(rs.getString("foto"));

				list.add(p);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Disciplina> listarDisciplina() {
		List<Disciplina> list = new ArrayList<Disciplina>();
		String sql = "SELECT * FROM Disciplinas";

		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setId(rs.getInt("id"));
				d.setNome(rs.getString("nome"));
				list.add(d);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<CD> listarCd(int i){
		List<CD> list = new ArrayList<CD>();
		String sql = "SELECT c.*, cl.nome AS nomeColaborador, d.nome AS nomeDisciplina FROM cd c "
				   + " INNER JOIN Colaboradores cl INNER JOIN Disciplinas d "
				   + " ON c.id_colaborador = cl.id AND c.id_disciplina = d.id WHERE c.id_colaborador = ? ";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CD cd = new CD();
				cd.setId(rs.getInt("id"));
				cd.setId_colaborador(rs.getInt("id_colaborador"));
				cd.setId_disciplina(rs.getInt("id_disciplina"));
				cd.setProfessor(new Professor(cd.getId_colaborador(), rs.getString("nomeColaborador"), null, null, null, null, null, null));
				cd.setDisciplina(new Disciplina(cd.getId_disciplina(), rs.getString("nomeDisciplina"), null, null, null));
				list.add(cd);
			}
		}catch (SQLException e ){
			e.printStackTrace();
		}
		return list;
	}
	
	public Professor buscaProfessor(int id) {
		String sql = "SELECT * FROM Colaboradores WHERE id = ? ";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Professor p = new Professor();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setCarga_hora(rs.getInt("carga_hora"));
				p.setRestante(rs.getInt("restante"));
				p.setTipo(rs.getInt("tipo"));
				p.setFoto(rs.getString("foto"));
				return p;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean editar(Professor p) {
		String sql = "UPDATE Colaboradores SET nome = ?, carga_hora = ?, restante = ?, tipo = ?, foto = ? WHERE id = ? ";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(6, p.getId());
			ps.setString(1, p.getNome());
			ps.setInt(2, p.getCarga_hora());
			ps.setInt(3, p.getRestante());
			ps.setInt(4, p.getTipo());
			ps.setString(5, p.getFoto());
			
			if(ps.executeUpdate() > 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean excluir(Integer id) {
		String sql = "DELETE FROM Colaboradores WHERE id = ? ";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			if (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean cdExcluir(Integer id) {
		String sql = "DELETE FROM CD WHERE id = ? ";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			if (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
