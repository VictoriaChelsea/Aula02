import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaisTest {
	public Pais pais, copia;
	public ArrayList<Pais> paises = new ArrayList<Pais>();
	
	static int id = 0;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("setup");
		pais = new Pais(id, "Brasil", 563008900, 6987234);
		copia = new Pais(id, "Brasil", 563008900, 6987234 );
		System.out.println(pais);
		System.out.println(copia);
		System.out.println(id);
	}
	@Test
	public void test00Carregar() {
		System.out.println("carregar");
		Pais fixture = new Pais(7, "Brasil", 563008900, 6987234);
		Pais novo = new Pais(7, "Brasil", 563008900, 6987234);
		novo.carregar();
		assertEquals("testa inclusao", novo, fixture);
	}
	
	@Test
	public void test01Criar() {
		System.out.println("criar");
		pais.criar();
		id = pais.getId();
		System.out.println(id);
		copia.setId(id);
		assertEquals("testa criacao", pais, copia);
	}
	@Test
	public void test02Atualizar() {
		System.out.println("atualizar");
		pais.setPopulacao(563008900);
		copia.setPopulacao(563008900);
		pais.atualizar();
		pais.carregar();
		assertEquals("testa atualizacao", pais, copia);
	}
	
	@Test
	public void test03Excluir() {
		System.out.println("excluir");
		copia.setId(-1);
		copia.setNome("Brasil");
		copia.setPopulacao(563008900);
		copia.setArea(6987234.0);
		copia.toString();
		
		pais.excluir();
		pais.carregar();
		pais.toString();
		assertEquals("testa exclusao", pais, copia);
	}
	@Test
	public void Test04CarregaTresPaises() {
		System.out.println("Test04CarregaTresPaises");
		pais.carregarPaises(3);
		
		assertEquals("quantidade paises", 3, pais.paises.size());
	}
	
	@Test
	public void teste05MaiorNumeroHabitantes() {
		System.out.println("teste05MaiorNumeroHabitantes");
		pais.carregarPaisMaiorNumeroHabitantes();
		
		copia.setNome("Holanda");
		copia.setPopulacao(500674138);
		
		assertEquals("Testa nome", pais.paisMaiorPopulacao.nome, "Holanda");
		assertEquals("Testa numero habitantes", pais.paisMaiorPopulacao.populacao, 500674138);
	}
	
	@Test
	public void Test06carregarPaisMenorArea() {
		System.out.println("Test06carregarPaisMenorArea");
		pais.carregarPaisMenorArea();
		assertEquals("Area", pais.paisMenorArea.nome, "Brasil");
	}
	
	
}
