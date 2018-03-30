import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Pais {

	
	int id;
	String nome;
	long populacao;
	double area;
	public ArrayList<Pais> paises = new ArrayList<Pais>();
	public Pais paisMaiorPopulacao;
	public Pais paisMenorArea;

	//Bancooo
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public Connection obtemConexao() throws SQLException {
		return DriverManager
				.getConnection("jdbc:mysql://localhost/prat_prog?user=root&password=");
	}
	
	public void paisMenorNumeroHabitantes() {
		
	}
	
	

	  
	public void criar() {
		String sqlInsert = "INSERT INTO pais(nome, populacao, area) VALUES (?, ?, ?)";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			stm.setString(1, getNome());
			stm.setLong(2, getPopulacao());
			stm.setDouble(3, getArea());
			stm.execute();
			String sqlQuery = "SELECT LAST_INSERT_ID()";
			try(PreparedStatement stm2 = conn.prepareStatement(sqlQuery);
					ResultSet rs = stm2.executeQuery();) {
				if(rs.next()){
					setId(rs.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void atualizar() {
		String sqlUpdate = "UPDATE pais SET nome=?, populacao=?, area=? WHERE id=?";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			stm.setString(1, getNome());
			stm.setLong(2, getPopulacao());
			stm.setDouble(3, getArea());
			stm.setInt(4, getId());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void excluir() {
		String sqlDelete = "DELETE FROM pais WHERE id = ?";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
			stm.setInt(1, getId());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregar() {
		String sqlSelect = "SELECT nome, populacao, area FROM pais WHERE pais.id = ?";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setInt(1, getId());
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					setNome(rs.getString("nome"));
					setPopulacao(rs.getLong("populacao"));
					setArea(rs.getDouble("area"));
				} else {
					setId(-1);
					setNome(null);
					setPopulacao((Long) null);
					setArea((Double) null);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
	}
	
	public void carregarPaises(int quantidadePaises) {
		String sqlSelect = "SELECT * FROM pais LIMIT ?";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setInt(1, quantidadePaises);
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String nome = rs.getString("nome");
					long populacao = rs.getLong("populacao");
					double area = rs.getDouble("area");
					
					paises.add(new Pais(id, nome, populacao, area));
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
	}
	
	public void carregarPaisMaiorNumeroHabitantes() {
		String sqlSelect = "SELECT * FROM pais ORDER BY populacao DESC LIMIT 1";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String nome = rs.getString("nome");
					long populacao = rs.getLong("populacao");
					double area = rs.getDouble("area");
					
					this.paisMaiorPopulacao = new Pais(id, nome, populacao, area);
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
	}
	
	public void carregarPaisMenorArea() {
		String sqlSelect = "SELECT * FROM pais ORDER BY area asc LIMIT 1";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String nome = rs.getString("nome");
					long populacao = rs.getLong("populacao");
					double area = rs.getDouble("area");
					
					this.paisMenorArea = new Pais(id, nome, populacao, area);
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
	}


	public Pais() {
		
	}
	
	public Pais(int id, String nome, long populacao, double area) {
		this.id = id;
		this.nome = nome;
		this.populacao = populacao;
		this.area = area;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getPopulacao() {
		return populacao;
	}

	public void setPopulacao(long populacao) {
		this.populacao = populacao;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		if (Double.doubleToLongBits(area) != Double.doubleToLongBits(other.area))
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (paisMaiorPopulacao == null) {
			if (other.paisMaiorPopulacao != null)
				return false;
		} else if (!paisMaiorPopulacao.equals(other.paisMaiorPopulacao))
			return false;
		if (paises == null) {
			if (other.paises != null)
				return false;
		} else if (!paises.equals(other.paises))
			return false;
		if (populacao != other.populacao)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pais [id=" + id + ", nome=" + nome + ", populacao=" + populacao + ", area=" + area + "]";
	}
	
}
