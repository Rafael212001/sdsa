package br.com.MBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.DAO.disciplinaDAO;
import br.com.entities.Disciplina;

@ManagedBean
@ViewScoped
public class DisciplinaMB {
	Disciplina disc = new Disciplina();
	Disciplina selc;
	List<Disciplina> discL;
	List<Disciplina> lista;
	disciplinaDAO dDAO = new disciplinaDAO();
	private List<Integer> semestres;
	int ID;
	FacesContext context;

	public DisciplinaMB() {
		listarD();
	}

	public void salvar() {
		context = FacesContext.getCurrentInstance();
		if (disc.getId() != null) {
			Disciplina d = dDAO.buscarDisciplina(disc.getId());
			if (d != null && d.getId().equals(disc.getId())) {
				editarDisciplina();
			}
		} else {
			criarDisciplina();
		}
	}

	public void editarDisciplina() {
		if (testarCampos()) {
			if (dDAO.editar(disc)) {
				System.out.println("SDSA:Disciplina alterada.");
				context.addMessage(null, new FacesMessage("Sucesso", "Disciplina alterado."));
				zerar();
				listarD();
			} else {
				System.out.println("SDSA:Erro na altera��o da disciplina.");
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na altera��o da disciplina."));
				listarD();
			}
		} else {
			System.out.println("SDSA:Campo vazio.");
		}
	}

	public void criarDisciplina() {
		if (testarCampos()) {
			disc.setId_curso(ID);
			if (dDAO.inserir(disc)) {
				System.out.println("SDSA:Disciplina criada.");
				context.addMessage(null, new FacesMessage("Sucesso", "Disciplina criada."));
				zerar();
				listarD();
			} else {
				System.out.println("SDSA:Erro na cria��o da disciplina.");
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na cria��o da disciplina."));
				listarD();
			}
		} else {
			System.out.println("SDSA:Campo vazio.");
		}
	}

	private boolean testarCampos() {
		if ((disc.getNome().equals("")) || (disc.getCarga_hora() == null) || (disc.getSemestre() == null)) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Campo(s) vazio(s)", "Algum campo est� vazio."));
			return false;
		} else {
			return true;
		}
	}

	private void zerar() {
		disc = new Disciplina();
		dDAO = new disciplinaDAO();
		lista = null;
		selc = null;
	}

	public void editar() {
		disc = selc;
	}

	public void excluir() {
		context = FacesContext.getCurrentInstance();
		if (dDAO.excluir(selc.getId())) {
			System.out.println("SDSA:Disciplina " + selc.getNome() + " excluida.");
			context.addMessage(null, new FacesMessage("Excluido", "Disciplina " + selc.getNome() + " excluida."));
			delit();
			listarD();
			zerar();
		} else {
			System.out.println("SDSA:Erro ao excluir.");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"� necess�rio tirar os colaboradores dessa disciplina."));
		}
	}
	
	public void delit() {
		if(dDAO.delit(selc.getId())) {
			System.out.println("SDSA:Aula deletada.");
		}else {
			System.out.println("SDSA:Erro ao deletar aula.");
		}
	}

	public void listarSemestreC() {
		int i = dDAO.listarId(ID);
		semestres = new ArrayList<Integer>();
		for (int f = 1; f <= i; f++) {
			semestres.add(f);
		}

	}

	public String fechar() {
		zerar();
		listarD();
		return "PF('dlg6').hide();";
	}

	public void listarD() {
		discL = dDAO.listarTodos(ID);
	}

	public Disciplina getDisc() {
		return disc;
	}

	public void setDisc(Disciplina disc) {
		this.disc = disc;
	}

	public List<Disciplina> getDiscL() {
		return discL;
	}

	public void setDiscL(List<Disciplina> discL) {
		this.discL = discL;
	}

	public disciplinaDAO getdDAO() {
		return dDAO;
	}

	public void setdDAO(disciplinaDAO dDAO) {
		this.dDAO = dDAO;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public List<Integer> getSemestres() {
		return semestres;
	}

	public void setSemestres(List<Integer> semestres) {
		this.semestres = semestres;
	}

	public Disciplina getSelc() {
		return selc;
	}

	public void setSelc(Disciplina selc) {
		this.selc = selc;
	}

	public List<Disciplina> getLista() {
		return lista;
	}

	public void setLista(List<Disciplina> lista) {
		this.lista = lista;
	}

}